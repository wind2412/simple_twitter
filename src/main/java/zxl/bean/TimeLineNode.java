package zxl.bean;

/**
 * 某一用户的时间线节点。会被放到timeline表中。
 * 同时，我们也设置timeline:[UID]表。
 * @author zhengxiaolin
 *
 */
public class TimeLineNode {

	private long TID;			//此条时间线编号
	private long UID;			//某一个用户，他做了什么事情，然后会放到时间线中。
	private long type;			//timeline node类型： 0=>UID发的推文  1=>UID回复了哪篇文章  2=>UID转发了哪篇文章 /* 3=>UID赞了哪篇文章 --deleted*/ 
	private long target_AID;	//所谓的“哪篇文章”
	private long time;			//时间线上的这个节点的产生时间
	
	public TimeLineNode(long TID, long UID, long type, long target_AID, long time) {
		super();
		this.TID = TID;
		this.UID = UID;
		this.type = type;
		this.target_AID = target_AID;
		this.time = time;
	}

	public long getTID() {
		return TID;
	}

	public void setTID(long tID) {
		TID = tID;
	}

	public long getUID() {
		return UID;
	}

	public void setTarget_UID(long UID) {
		this.UID = UID;
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
