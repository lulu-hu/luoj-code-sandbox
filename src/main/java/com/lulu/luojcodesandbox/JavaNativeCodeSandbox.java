package com.lulu.luojcodesandbox;

import com.lulu.luojcodesandbox.model.ExecuteCodeRequest;
import com.lulu.luojcodesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * @author lulu
 * @version 1.0
 * @description 原生代码沙箱实现 （直接复用模板方法）
 * @date 2024/7/11 15:28
 */
@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
