package Server;

public class socketTesting {
	
	public String login(String username, String password) {
		String logincheck =  "";
		//System.out.println("'database' login reached in testing");
		if (username.equals("gd113")&& (password.equals("1234"))){
				 logincheck = "true";
						}else{ logincheck = "false";}
		
		return logincheck;
		
	}

	public String dockStatus() {
		System.out.println("'database' dockstatus reached in testing");
		String dock = "dock something something";
		
		return dock;
	}


}

