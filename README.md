# TaskPro
Task Management System:

Приложение управления задачами:

По ходу выполнения проекта были использованы следующие технологии:

Spring:

  Boot
  
  Data JPA
  
  Security
  
PostgreSQl

Hibernate

Lombok

ModelMapper

Через класс имплементирующий CommandLineRunner добавляются некоторые данные в таблицы.

Сами же таблицы генерируются автоматически как указано в файле application.properties(spring.jpa.hibernate.ddl-auto=create-drop).

Первоначально,в БД есть 2 роли:Админ и Пользователь.

Для каждой роли свои права доступа и так далее...

В целом,было реализовано все жизненнонеобходимое для такого приложения.Упустил лишь моменты,где код для разных контроллеров и сами представления схожи. 
