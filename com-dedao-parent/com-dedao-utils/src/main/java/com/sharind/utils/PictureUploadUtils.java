package com.sharind.utils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/** 图片上传工具类
 * @author 5
 *
 */
public class PictureUploadUtils {
	
	/** 图片上传,要求前端图片上传类型为Multipart
	 * @param request
	 * @param path2 
	 * @param username
	 * @return
	 */
	public static String upload(HttpServletRequest request, String path) {
		
		Map<String,Object> returnMap = new HashMap<>();
		MultipartHttpServletRequest multipartRequest = null;
		boolean flag = request instanceof MultipartHttpServletRequest;
		// 上传的图片路径
		String filePath = null;
		if(flag){
		
		try {
			multipartRequest = (MultipartHttpServletRequest) request;
		// 允许上传的图片类型
    	String[] allowExtName = {".bmp",".jpg",".jpeg",".png",".gif"};
        List<MultipartFile> multipartFiles = getFileSet(request, 1024 * 20, allowExtName);
        //String path = "C:" + File.separator;
        // 图片保存的目录
        //String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
        //String path = "F:/lcl/image";
        if (multipartFiles.size() == 0) {
            // TODO 给出提示,不允许没选择文件点击上传
        }
        for (MultipartFile multipartFile : multipartFiles) {
                filePath = uploadFile(multipartFile, path);
                System.out.println(filePath);
                  // 把图片存储的路径保存到数据库
//                User user = new User();
//                user.setUsername(username);
//                user.setImage(filePath);
//                userService.updateUserImage(user);
                
        	}
    	
		} catch (Exception e) {
			e.printStackTrace();
			
			}
		
		}
		return filePath;
	
	}
	
	 /**
     * @descrption 根据HttpServletRequest对象获取MultipartFile集合
     * @author xdwang
     * @create 2012-11-19下午5:11:41
     * @param request
     * @param maxLength
     *            文件最大限制
     * @param allowExtName
     *            不允许上传的文件扩展名
     * @return MultipartFile集合
     */
    public static  List<MultipartFile> getFileSet(HttpServletRequest request,
            long maxLength, String[] allowExtName) {
        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch (Exception e) {
        	e.printStackTrace();
            return new LinkedList<MultipartFile>();
        }

        List<MultipartFile> list = new LinkedList<MultipartFile>();
        List<MultipartFile> files = multipartRequest.getFiles("image");
        // 获取符合条件的
        for (int i = 0; i < files.size(); i++) {
            if (validateFile(files.get(i), maxLength, allowExtName)) {
            	list.add(files.get(i));
            }
            if(i==files.size()){
            	return list;
            }
        }
		return list;
    }
    
    /**
     * @descrption 验证文件格式，这里主要验证后缀名
     * @author xdwang
     * @create 2012-11-19下午4:08:12
     * @param file
     *            MultipartFile对象
     * @param maxLength
     *            文件最大限制
     * @param allowExtName
     *            不允许上传的文件扩展名
     * @return 文件格式是否合法
     * 
     */
    private static  boolean validateFile(MultipartFile file, long maxLength,
            String[] allowExtName) {
    	// 过滤图片大小
//        if (file.getSize() < 0 || file.getSize() > maxLength){
//        	return false;
//        }
        String filename = file.getOriginalFilename();

        // 处理不选择文件点击上传时，也会有MultipartFile对象，在此进行过滤
        if (filename == "") {
            return false;
        }
        // 过滤图片后缀
        String extName = filename.substring(filename.lastIndexOf("."))
                .toLowerCase();
        // 判断上传图片的后缀是否符合
        boolean contains = Arrays.asList(allowExtName).contains(extName);
        if (contains) {
            return true;
        } else {
            return false;
        }
    }
    
    
    /**
     * @descrption 保存文件
     * @author xdwang
     * @create 2012-11-19下午4:17:36
     * @param file
     *            MultipartFile对象
     * @param path
     *            保存路径，如“D:\\File\\”
     * @return 保存的全路径 如“D:\\File\\2345678.txt”
     * @throws Exception
     *             文件保存失败
     */
    public static String uploadFile(MultipartFile file, String path)
            throws Exception {

        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."))
                .toLowerCase();
        String lastFileName = UUID.randomUUID().toString() + extName;
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File temp = new File(path);
        if (!temp.isDirectory()) {
            temp.mkdir();
        }
        // 图片存储的全路径
        String fileFullPath = path + lastFileName;
        FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
       
        //return fileFullPath;
        return lastFileName;
    }
    
    public static String uploadFileToService(MultipartFile file){
    	//获取原文件名
		String oldFileName = file.getOriginalFilename();
		//生成新文件名
		String newFileName = UUID.randomUUID().toString()+oldFileName.substring(oldFileName.lastIndexOf("."));
    	return newFileName;
    }
    
    public static String suffix(String string){
    	//从最后一个"."开始截取字符串到最后
    	String substring = string.substring(string.lastIndexOf("."));
    	return substring;
    }
    
    public static String prefix(String string){
    	//从最后一个"."开始截取字符串到最后
    	String substring = string.substring(0,string.lastIndexOf("."));
    	return substring;
    }
}
