package com.jst.invoice.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.util.Assert;

import com.jst.prodution.constants.ApiConstants;
import com.jst.prodution.util.ILogger;


/**
 * 文件上传、下载工具类
 * @author Administrator
 *
 */
public class FastDfsUtils {
	
	private static ILogger logger = new ILogger(ApiConstants.JST_OMS, FastDfsUtils.class);
	
	private static TrackerClient trackerClient = null;
	
	static{
		//String file = FastDfsUtils.class.getClassLoader().getResource("config/fdfs_client.conf").getFile();
		try {
			ClientGlobal.init("config/fdfs_client.conf");
			trackerClient = new TrackerClient();
		} catch (Exception e) {
			logger.error("加载文件异常：", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取文件字节流
	 * @param inStream
	 * @param fileLength
	 * @return
	 * @throws IOException
	 */
	private static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {
        byte[] buffer = new byte[256 * 1024];
        byte[] fileBuffer = new byte[(int) fileLength];
        int count = 0;
        int length = 0;
        while ((length = inStream.read(buffer)) != -1) {
            for (int i = 0; i < length; ++i) {
                fileBuffer[count + i] = buffer[i];
            }
            count += length;
        }
        return fileBuffer;
    }
	
	/**
	 * 文件上传
	 * @param file 文件（File类型）
	 * @param uploadFileName 文件名称
	 * @param fileLength 文件流长度
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile(File file, String uploadFileName, long fileLength) throws Exception {
        byte[] fileBuff = getFileBuffer(new FileInputStream(file), fileLength);
        String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
        	logger.error("Fail to upload file, because the format of filename is illegal.");
            return null;
        }
        TrackerServer tracker = null;
        // 设置元信息
        NameValuePair[] metaList = getFileNameValuePair(uploadFileName,fileExtName,fileLength);
        // 上传文件
        try {
        	tracker = trackerClient.getConnection();
			StorageClient1 sClient = new StorageClient1(tracker,null);
			String filePath = sClient.upload_file1(fileBuff, fileExtName, metaList);
			return filePath;
        }finally{
			if (tracker != null) {
				try {
					tracker.close();
				} catch (Exception e) {
					logger.error("加载文件异常：", e);
					e.printStackTrace();
				}
			}
		}
    }
	
	/**
	 * 获取上传头参数
	 * @param uploadFileName
	 * @param fileLength
	 * @return
	 */
	public static NameValuePair[] getFileNameValuePair(String uploadFileName,String fileExtName,Long fileLength){
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", uploadFileName);
	    metaList[1] = new NameValuePair("fileExtName", fileExtName);
	    metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));
		return metaList;
	}
	
	/**
	 * 文件上传
	 * @param file 文件流
	 * @param fileName 文件名称
	 * @param meta_list
	 * @return
	 * @throws Exception
	 */
	public static String upload(byte[] file,String uploadFileName,long fileLength) throws Exception{
		if(file == null || file.length == 0){
			throw new Exception("上传文件不能为空");
		}
		Assert.notNull(uploadFileName);
		TrackerServer tracker = null;
		String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        }else {
        	logger.error("Fail to upload file, because the format of filename is illegal.");
            return null;
        }
        NameValuePair[] metaList = getFileNameValuePair(uploadFileName,fileExtName,fileLength);
		try{
			tracker = trackerClient.getConnection();
			StorageClient1 sClient = new StorageClient1(tracker,null);
			//String[] feedback = sClient.upload_file(file, fileName, meta_list);
			//String fullDir = feedback[0] + "/" + feedback[1];
			//return fullDir
			String filePath = sClient.upload_file1(file, fileExtName, metaList);
			return filePath;
		}finally{
			if (tracker != null) {
				try {
					tracker.close();
				} catch (Exception e) {
					logger.error("加载文件异常：", e);
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 文件下载
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static byte[] download(String url) throws Exception{
		TrackerServer tracker = null;
		try {
			tracker = trackerClient.getConnection();
			StorageClient1 sClient = new StorageClient1(tracker,null);
			byte[] file = sClient.download_file1(url);
			return file;
		}finally{
			try {
				if(tracker != null){
					tracker.close();
				}
			} catch (Exception e) {
				logger.error("加载文件异常：", e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除文件
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static int delete(String url) throws Exception{
		TrackerServer tracker = null;
		try {
			tracker = trackerClient.getConnection();
			StorageClient1 client = new StorageClient1(tracker,null);
			return client.delete_file1(url);
		}finally {
			try {
				if (tracker != null) {
					tracker.close();
				}
			} catch (Exception e) {
				logger.error("加载文件异常：", e);
				e.printStackTrace();
			}
		}
	}
}
