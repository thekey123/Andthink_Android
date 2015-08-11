package com.andthink.task;

/**
 * 进度更新发送者
 * 
 * @author Mr.zheng
 *
 */
public interface ProgressSender {
	/**
	 * 通知进度更新
	 * 
	 * @param current
	 *            当前进度
	 * @param total
	 *            总量
	 * @param exraData
	 *            额外的数据，可以传null
	 */
	public void send(long current, long total, Object exraData);
}
