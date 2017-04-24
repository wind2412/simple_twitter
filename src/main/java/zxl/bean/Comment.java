package zxl.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Comment {

	private long time;			//发布时间
	private String content;		//评论的内容
	private String path;		//评论在服务器的路径
	private long UID;			//评论者代号
	private long CID;			//此评论编号
	private long AID;			//所评论的推文的编号
	
	//评论被点赞和评论被评论的功能暂时不考虑
	
	public Comment(String content, long uID, long aID) {
		super();
		this.content = content;
		UID = uID;
		AID = aID;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getUID() {
		return UID;
	}

	public void setUID(long uID) {
		UID = uID;
	}

	public long getCID() {
		return CID;
	}

	public void setCID(long cID) {
		CID = cID;
	}

	public long getAID() {
		return AID;
	}

	public void setAID(long aID) {
		AID = aID;
	}
	
	public void add_comment_to_disk() throws IOException {
		if(this.path != null && this.content != null) {
			File file = new File(this.path);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bw.write(this.content);
			bw.close();
		}
	}
	
}
