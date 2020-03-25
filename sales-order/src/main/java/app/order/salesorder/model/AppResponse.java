package app.order.salesorder.model;

public final class AppResponse<T> {
	private final T data;
	private final String info;
	
	public AppResponse(T data) {
		super();
		this.data = data;
		this.info = null;
	}
	
	public AppResponse(T data, String info) {
		super();
		this.data = data;
		this.info = info;
	}
	public T getData() {
		return data;
	}
	public String getInfo() {
		return info;
	}
}
