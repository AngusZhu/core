package com.cloverframework.core;
/**
 * 演示两点：
 * 	1.对象可以在被GC时自我救赎
 * 	2.这种救赎的机会只有一次，因为一个对象finalize方法最多只会被系统自动调用一次
 * @author AngusZhu
 *
 */
public class SaveSelf {

	public static SaveSelf SAVE_HOOK=null;

	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method executed..");
		SaveSelf.SAVE_HOOK=this;
	}

	protected static void isAlive() throws Throwable {
		System.out.println("Yes,I'm still alive :)" );
	}
	
	public static void main(String[] args) throws Throwable {
		SAVE_HOOK=new SaveSelf();
		//对象第一次成功拯救自己
		SAVE_HOOK=null;
		System.gc();
		Thread.sleep(500);
		if(SAVE_HOOK!=null){
			SAVE_HOOK.isAlive();
		}else{
			System.out.println("no ,i'm dead :<");
		}
		//救赎失败
		SAVE_HOOK=null;
		System.gc();
		Thread.sleep(500);
		if(SAVE_HOOK!=null){
			SAVE_HOOK.isAlive();
		}else{
			System.out.println("no ,i'm dead :<");
		}
	}
}
