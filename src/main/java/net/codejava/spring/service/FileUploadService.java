package net.codejava.spring.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public class FileUploadService {
	private static final String REALNAME = "realName";
	private static final String STORENAME = "storeName";
	private static final String SIZE = "size";
	private static final String SUFFIX = "suffix";
	private static final String CONTENTTYPE = "contentType";
	private static final String CREATETIME = "createTime";
	private static final String UPLOADDIR = "uploadDir/";

	/**
	 * ���ϴ����ļ�����������
	 * 
	
	 */
	private static String rename(String name) {

		Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));
		Long random = (long) (Math.random() * now);
		String fileName = now + "" + random;

		if (name.indexOf(".") != -1) {
			fileName += name.substring(name.lastIndexOf("."));
		}

		return fileName;
	}

	/**
	 * ѹ������ļ���
	
	 */
	private static String zipName(String name) {
		String prefix = "";
		if (name.indexOf(".") != -1) {
			prefix = name.substring(0, name.lastIndexOf("."));
		} else {
			prefix = name;
		}
		return prefix + ".zip";
	}

	/**
	 * �ϴ��ļ�
	 * 
	
	 */
	public static List<Map<String, Object>> upload(HttpServletRequest request,
			String[] params, Map<String, Object[]> values) throws Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();

		String uploadDir = request.getSession().getServletContext()
				.getRealPath("/")
				+ FileUploadService.UPLOADDIR;
		File file = new File(uploadDir);

		if (!file.exists()) {
			file.mkdir();
		}

		String fileName = null;
		int i = 0;
		for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()
				.iterator(); it.hasNext(); i++) {

			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();

			fileName = mFile.getOriginalFilename();

			String storeName = rename(fileName);

			String noZipName = uploadDir + storeName;
			String zipName = zipName(noZipName);

			// �ϴ���Ϊѹ���ļ�
			ZipOutputStream outputStream = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(zipName)));
			outputStream.putNextEntry(new ZipEntry(fileName));
			outputStream.setEncoding("GBK");

			FileCopyUtils.copy(mFile.getInputStream(), outputStream);

			Map<String, Object> map = new HashMap<String, Object>();
			// �̶�����ֵ��
			map.put(FileUploadService.REALNAME, zipName(fileName));
			map.put(FileUploadService.STORENAME, zipName(storeName));
			map.put(FileUploadService.SIZE, new File(zipName).length());
			map.put(FileUploadService.SUFFIX, "zip");
			map.put(FileUploadService.CONTENTTYPE, "application/octet-stream");
			map.put(FileUploadService.CREATETIME, new Date());

			// �Զ������ֵ��
			for (String param : params) {
				map.put("param",param );
			}

			result.add(map);
		}
		return result;
	}

	/**
	 * ����
	 * 
	
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String contentType,
			String realName) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ FileUploadService.UPLOADDIR;
		String downLoadPath = ctxPath + storeName;

		long fileLength = new File(downLoadPath).length();

		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
}

