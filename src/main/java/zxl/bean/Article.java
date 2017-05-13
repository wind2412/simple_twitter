package zxl.bean;

import java.util.List;


public class Article {
	
	/**
	 * 发一篇文章有7种方式：
	 * 1.普通推文
	 * 2.回复别人的推文
	 * 3.转发别人的推文
	 * 4.回复别人的回复	=>	由于回复和转发也会变成推文。因此相当于“回复别人的推文”。同2.
	 * 5.转发别人的回复	=>	同3.
	 * 6.回复别人的转发	=>	同2.
	 * 7.转发别人的转发	=>	同3.
	 * 
	 * 因此分析后发现只有3种方式。	也就是，只有“正在创建”的推文有类型type。而被回复/转发的推文没有type，全是某一文章，全用trans_AID代替。这样可以架构清晰。
	 */
	
	private long time;			//发布时间
	private String content;		//推文的内容
	private long UID;			//作者代号
	private long AID;			//文章编号
	private long type;			//此推文的类型。 0：普通推文  1：reply	2：trans		=>    某种程度reply和trans的实现是一样的。只是，reply的显示和trans不同。
	private long trans_AID;		//用作reply和trans的AID 即：此文章是一篇转发的文章，trans_AID是引用文章的AID.		//没有设为0.
	private String[] pics;
	private long TID;			//发表的这篇文章所对应的时间线编号
	private boolean isPrivate;	//是公开发表的吗？		//存入redis时，如果是公开就是0=>false，私有就是1=>true.
	
	public Article(String content, long UID, long type, long trans_AID, boolean isPrivate, String[] pics) {
		super();
		this.content = content;
		this.UID = UID;
		this.type = type;
		this.trans_AID = trans_AID;
		this.isPrivate = isPrivate;
		this.pics = pics;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public long getAID() {
		return AID;
	}

	public void setAID(long AID) {
		this.AID = AID;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getUID() {
		return UID;
	}

	public void setUID(long UID) {
		this.UID = UID;
	}

	public long getTrans_AID() {
		return trans_AID;
	}

	public void setTrans_AID(long trans_AID) {
		this.trans_AID = trans_AID;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}

	public long getTID() {
		return TID;
	}

	public void setTID(long tID) {
		TID = tID;
	}

	
	//由于网页一定会获取到评论和文章。如果再和磁盘进行交互，1.程序过于麻烦，2.还影响速度。而且，不如直接从redis内存中去取来得快啊。3.如果搜索引擎成功，直接通过内存库查找，还不是比磁盘更快吗。
//	public void add_article_to_disk() throws IOException {
//		if(this.path != null && this.content != null) {
//			File file = new File(this.path);
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//			bw.write(this.content);
//			bw.close();
//		}
//	}
	
}
