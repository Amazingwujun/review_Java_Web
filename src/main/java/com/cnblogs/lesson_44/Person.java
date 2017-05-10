package com.cnblogs.lesson_44;

public class Person {
	private PersonListener<Person> listener;

	public static void main(String[] args) {
		Person person = new Person();
		person.serListener(new PersonListener<Person>() {

			@Override
			public void doRun(Event<Person> e) {
				Person person = e.getSource();
				System.out.println(person + "在跑步");
			}

			@Override
			public void doEat(Event<Person> e) {
				Person person = e.getSource();
				System.out.println(person + "在吃饭");
			}
		});

		person.eat();
		person.run();
	}

	public void serListener(PersonListener<Person> listener) {
		this.listener = listener;
	}

	public void eat() {

		if (listener != null) {
			listener.doEat(new Event<>(this));
		}
	}

	public void run() {

		if (listener != null) {
			listener.doRun(new Event<>(this));
		}
	}

}
