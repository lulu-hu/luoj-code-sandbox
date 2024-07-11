package com.lulu.luojcodesandbox.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.List;

/**
 * @author lulu
 * @version 1.0
 * @description TODO
 * @date 2024/7/6 14:52
 */
public class DockerDemo {

    public static void main(String[] args) throws InterruptedException {
     // 获取默认的 Docker Client
     DockerClient dockerClient = DockerClientBuilder.getInstance().build();

//        // 拉取镜像
        String image = "nginx:latest";
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
        PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                super.onNext(item);
                System.out.println("下载镜像:" + item.getStatus());
            }
        };
        pullImageCmd
                .exec(pullImageResultCallback)
                .awaitCompletion();
        System.out.println("下载完成");
        // 创建容器
        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        CreateContainerResponse createContainerResponse = containerCmd
                .withCmd("echo", "Hello Docker")
                .exec();
        String containerId = createContainerResponse.getId();

        // 查看容器
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();
        List<Container> containerList = listContainersCmd.withShowAll(true).exec();
        for (Container container : containerList){
            System.out.println(container);
        }

        // 启动容器
        dockerClient.startContainerCmd(containerId).exec();

//        // 查看日志
//        LogContainerResultCallback logContainerResultCallback = new LogContainerResultCallback() {
//            @Override
//            public void onNext(Frame item) {
//                System.out.println(item.getStreamType());
//                System.out.println("日志：" + new String(item.getPayload()));
//                super.onNext(item);
//            }
//        };
//
//        // 阻塞等待日志输出
//        dockerClient.logContainerCmd(containerId)
//                .withStdErr(true)
//                .withStdOut(true)
//                .exec(logContainerResultCallback)
//                .awaitCompletion();
//
//        // 删除容器
//        dockerClient.removeContainerCmd("0e4c774b1207")
//                .withForce(true)
//                .exec();
//        System.out.println("删除成功");

        // 删除镜像
//        dockerClient.removeImageCmd(image)
//                .withForce(true)
//                .exec();
    }


}
