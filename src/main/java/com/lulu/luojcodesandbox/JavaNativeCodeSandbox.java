package com.lulu.luojcodesandbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.UUID;
import com.lulu.luojcodesandbox.model.ExecuteCodeRequest;
import com.lulu.luojcodesandbox.model.ExecuteCodeResponse;
import com.lulu.luojcodesandbox.model.ExecuteMessage;
import com.lulu.luojcodesandbox.utils.ProcessUtils;


/**
 * @author lulu
 * @version 1.0
 * @description TODO
 * @date 2024/7/2 15:15
 */
public class JavaNativeCodeSandbox implements CodeSandbox {


    public static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    public static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    public static void main(String[] args) {
        JavaNativeCodeSandbox javaNativeCodeSandbox = new JavaNativeCodeSandbox();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Arrays.asList("1 2", "3 4"));
        String code = ResourceUtil.readStr("test/simpleComputeArgs/Main.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        // 判断全局代码目录是否存在，没有则创建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }

        // 把用户代码隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);

        // 2. 编译代码，得到 class 文件
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");
            System.out.println(executeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // todo 3.执行代码，得到输出结果


        return null;
    }
}
