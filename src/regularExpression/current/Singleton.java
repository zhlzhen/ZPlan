package regularExpression.current;

public class Singleton {
	private Singleton() {
	}

	private static class SingletonHelper {
		private static final Singleton instance = new Singleton();
	}

	public static Singleton getInstance() {
		return SingletonHelper.instance;
	}
	/** 
     * readResolve方法应对单例对象被序列化时候 
     */  
    private Object readResolve() {  
        return getInstance();  
    }  
	
}