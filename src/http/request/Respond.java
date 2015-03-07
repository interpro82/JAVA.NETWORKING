package http.request;

public class Respond { 
		private int number;
		private String text;
		private String request;
		
		public Respond(int number, String text,String request) {
			this.number = number;
			this.text = text;
			this.request=request;
		}

		public Respond() {
			// TODO Auto-generated constructor stub
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getRequest() {
			return request;
		}

		public void setRequest(String request) {
			this.request = request;
		}
}
