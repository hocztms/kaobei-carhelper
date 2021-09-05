package com.kaobei.security.jwt;

import com.kaobei.util.BaiduUtil.AuthService;
import com.kaobei.util.BaiduUtil.Base64Util;
import com.kaobei.util.BaiduUtil.FileUtil;
import com.kaobei.util.HttpUtil;
import com.kaobei.commons.RestResult;
import com.kaobei.redis.RedisService;
import com.kaobei.security.config.WxLoginAuthenticationToken;
import com.kaobei.security.entity.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@Slf4j
public class JwtAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private RedisService redisService;


    public RestResult adminLogin(String username, String password) {
        System.out.println(username + password);
        Authentication authentication;
        try {
            // 进行身份验证,
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            redisService.setUserLoginLimit(username);
            return new RestResult(0, e.getMessage(), null);
        }

        MyUserDetails loginUser = (MyUserDetails) authentication.getPrincipal();
        RestResult result = new RestResult(1, "登入成功", null);

        log.info("管理员:{} 已经登入。。。本次权限为:{}", loginUser.getUsername(), loginUser.getAuthorities().toString());

        //主动失效 设置黑名单 并关闭已存在socket
        if (redisService.userLogoutByServer(username) == 0) {
            return null;
        }

        result.put("token", jwtTokenUtils.generateToken(loginUser, "admin"));
        return result;
    }

    public RestResult wxUserLogin(String code) {
        Authentication authentication;
        try {
            // 进行身份验证,
            authentication = authenticationManager.authenticate(
                    new WxLoginAuthenticationToken(code, "vxLogin"));
        } catch (Exception e) {
            e.printStackTrace();
            return new RestResult(0, e.getMessage(), null);
        }

        MyUserDetails loginUser = (MyUserDetails) authentication.getPrincipal();
        RestResult result = new RestResult(1, "登入成功", null);

        log.info("用户:{} 已经登入。。。本次权限为:{}", loginUser.getUsername(), loginUser.getAuthorities().toString());


        //主动失效 设置黑名单 并关闭已存在socket
        if (redisService.userLogoutByServer(loginUser.getUsername()) == 0) {
            return null;
        }

        result.put("token", jwtTokenUtils.generateToken(loginUser, "user"));
        return result;
    }


    public String getToken(HttpServletRequest request) {
        return request.getHeader(jwtTokenUtils.getHeader());
    }


    public String getAccountFromToken(HttpServletRequest request) {
        return jwtTokenUtils.getAuthAccountFromToken(request.getHeader(jwtTokenUtils.getHeader()));
    }


    public RestResult doneLoad(MultipartFile file) {
        if (file.isEmpty()){
            return new RestResult(0,"文件为空",null);
        }
        String fileName=null;
        String name=file.getOriginalFilename();
        String[] a = name.split("\\.");
        String type=a[a.length-1];
        RestResult result = null;
        if (type.equals("png")||type.equals("jpg")){
            fileName = String.valueOf(UUID.randomUUID())+"."+type;
            String path="C:\\upload";
            InputStream inputStream = null;
            File files = null;
            try {
                files = File.createTempFile("temp", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                file.transferTo(files);   //sourceFile为传入的MultipartFile
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream = new FileInputStream(files);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            files.deleteOnExit();
            OutputStream os = null;
            try {
                byte[] bs = new byte[1024];
                int len=1024;
                File tempFile = new File(path);
                if (!tempFile.exists()) {
                    tempFile.mkdirs();
                }
                os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
                while (true) {
                    len = inputStream.read(bs) ;
                    if (len==-1){
                        break;
                    }
                    os.write(bs, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result = new RestResult(1, "文件传输成功", fileName);
        }
        return result;
    }

    public RestResult getPlateByPicture(String fileName) {
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        try {
            // 本地文件路径
            String filePath = "C:\\upload\\"+fileName;//[本地文件路径]
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();//[调用鉴权接口获取的token]
            return new RestResult(1,"识别成功", HttpUtil.post(url, accessToken, param));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult(0,"识别错误",null);
    }
}
