package com.lulu.luojcodesandbox.utils;

import com.lulu.luojcodesandbox.model.ExecuteMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lulu
 * @version 1.0
 * @description 进程工具类
 * @date 2024/7/2 16:43
 */
public class ProcessUtils {

    /**
     *  执行进程并获取信息
     * @param runProcess
     * @param opName
     * @return
     */
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess,String opName) {

        ExecuteMessage executeMessage = new ExecuteMessage();

        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            if (exitValue == 0) {
                System.out.println(opName +"成功");
                // 分批获取进程的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> ouputStrList = new ArrayList<>();
                // 逐行读取编译信息
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    ouputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(ouputStrList, "\n"));
            } else {
                System.out.println( opName +"失败,错误码：" + exitValue);
                // 分批获取进程的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> ouputStrList = new ArrayList<>();
                // 逐行读取编译信息
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    ouputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(ouputStrList, "\n"));

                // 分批获取进程的错误输出
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                // 逐行读取编译信息

                List<String> errorOuputStrList = new ArrayList<>();
                // 逐行读取编译信息
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorOuputStrList.add(errorCompileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(errorOuputStrList, "\n"));
            }
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }
}
