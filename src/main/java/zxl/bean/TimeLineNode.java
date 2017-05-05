package zxl.bean;

/**
 * 某一用户的时间线节点。会被放到timeline:[UID]中。
 * @author zhengxiaolin
 *
 */
public class TimeLineNode {

	private long target_UID;	//自己所关注的用户
	private long type;			//timeline node类型： 0=>target_UID发的推文  1=>target_UID赞了哪篇文章   2=>target_UID回复/转发了哪篇文章
	private long target_AID;	//所谓的“哪篇文章”
	private long time;			//时间线上的这个节点的产生时间
	
	public TimeLineNode(long target_UID, long type, long target_AID, long time) {
		super();
		this.target_UID = target_UID;
		this.type = type;
		this.target_AID = target_AID;
		this.time = time;
	}

	public long getTarget_UID() {
		return target_UID;
	}

	public void setTarget_UID(long target_UID) {
		this.target_UID = target_UID;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public long getTarget_AID() {
		return target_AID;
	}

	public void setTarget_AID(long target_AID) {
		this.target_AID = target_AID;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
}
