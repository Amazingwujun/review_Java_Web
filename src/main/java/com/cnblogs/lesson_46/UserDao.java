package com.cnblogs.lesson_46;

public interface UserDao {
	User find(String username);
	
	User find(User u);
}
