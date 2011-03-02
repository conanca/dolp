package gs.dolp.common.util;

public class ExceptionHandler {
	public static String packageException(Throwable exception) {
		StringBuilder sb = new StringBuilder(exception.toString());
		sb.append("\n");
		for (StackTraceElement ele : exception.getStackTrace()) {
			sb.append("\t");
			sb.append(ele.toString());
			sb.append("\n");
		}
		Throwable cause = exception.getCause();
		sb.append("Caused by: ");
		sb.append(cause.toString());
		sb.append("\n");
		for (StackTraceElement ele : cause.getStackTrace()) {
			sb.append("\t");
			sb.append(ele.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
