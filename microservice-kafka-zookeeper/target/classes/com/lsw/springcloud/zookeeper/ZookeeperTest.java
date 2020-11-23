package com.lsw.springcloud.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperTest {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// TODO Auto-generated method stub
		ZooKeeper zk = new ZooKeeper("localhost:2181", 30000, new TestWatcher()); // 1  创建zookeeper,watcher为监控节点信息的回调函数.
		String node = "/node3";
		Stat stat = zk.exists(node, false); // 2检查节点是否存在
		// 2.1如果没存在则创造节点，并写数据test.
		if (null == stat) {
			String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, // 2.1 创建目录节点
					CreateMode.PERSISTENT);
			System.out.println("-----------------进入if-----------------");
			System.out.println(createResult);
			System.out.println("----------------------------------");
		}
		byte[] b = zk.getData(node, false, stat); // 3获取节点数据
		System.out.println("----------------getData------------------");
		System.out.println(new String(b));
		System.out.println("----------------------------------");
		zk.close();
	}

}

class TestWatcher implements Watcher {

	@Override
	public void process(WatchedEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("----------------------------------");
		System.out.println("path:" + arg0.getPath());
		System.out.println("type:" + arg0.getType());
		System.out.println("state:" + arg0.getState());
		System.out.println("----------------------------------");
	}
}