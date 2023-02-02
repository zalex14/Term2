import diary.Type;
import diary.exception.IncorrectArgumentException;
import diary.exception.TaskNotFoundException;
import diary.task.*;
import diary.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Сервис управления задачами по типу ежедневника
 */
public class Main {
    public static void main(String[] args) throws IncorrectArgumentException, TaskNotFoundException {
        System.out.println(" \nКурсовая работа «Java Core» \nСервис управления задачами по типу ежедневника\n ");

// Ввод данных явно через переменные
        DailyTask[] dailyTasks = new DailyTask[]{
                new DailyTask("Пробуждение", "Пробуждение и подьем", LocalDateTime.of(2023, 2, 2, 7, 0), Type.PERSONAL),
                new DailyTask("Зарядка", "Зарядка, умывание, закаливающие процедуры", LocalDateTime.of(2023, 2, 27, 7, 30), Type.PERSONAL),
                new DailyTask("Завтрак", "Сварить кашу, заварить какао", LocalDateTime.of(2023, 2, 2, 8, 0), Type.PERSONAL),
                new DailyTask("Работа", "Приступить к работе", LocalDateTime.of(2023, 2, 2, 9, 0), Type.WORK),
                new DailyTask("Ужин", "Поужинать", LocalDateTime.of(2023, 2, 2, 19, 0), Type.PERSONAL),
                new DailyTask("Сон", "Ложиться спать", LocalDateTime.of(2023, 2, 3, 23, 0), Type.PERSONAL)
        };


        WeeklyTask[] weeklyTasks = new WeeklyTask[]{
                new WeeklyTask("Магазин", "Сьездить за продуктами на неделю", LocalDateTime.of(2023, 2, 3, 7, 0), Type.PERSONAL),
                new WeeklyTask("Планерка", "Посетить еженедельную планерку", LocalDateTime.of(2023, 1, 27, 7, 30), Type.WORK),
                new WeeklyTask("Уборка", "Помыть пол и сделать уборку в квартире", LocalDateTime.of(2023, 1, 28, 8, 0), Type.PERSONAL)
        };

        MonthlyTask[] monthlyTasks = new MonthlyTask[]{
                new MonthlyTask("Квартплата", "Оплатить услуги ЖКХ", LocalDateTime.of(2023, 1, 27, 7, 0), Type.PERSONAL),
                new MonthlyTask("Месячное задание", "Согласовать месячное задание", LocalDateTime.of(2023, 1, 27, 9, 0), Type.WORK),
                new MonthlyTask("Месячный отчет", "Подготовить ежемесячный отчет", LocalDateTime.of(2023, 1, 31, 18, 0), Type.WORK)
        };

        YearlyTask[] yearlyTasks = new YearlyTask[]{
                new YearlyTask("Новый год", "Встретить Новый год", LocalDateTime.of(2023, 2, 1, 0, 0), Type.PERSONAL),
                new YearlyTask("Отпуск", "Написать заявление на отпуск", LocalDateTime.of(2023, 7, 1, 9, 0), Type.WORK),
                new YearlyTask("Получить 13 зарплату", "Сходить в кассу за ежегодной премией", LocalDateTime.of(2023, 3, 30, 10, 30), Type.WORK)
        };

        OneTimeTask[] oneTimeTasks = new OneTimeTask[]{
                new OneTimeTask("Кинотеатр", "Заказать билеты в кино", LocalDateTime.of(2023, 2, 4, 20, 0), Type.PERSONAL),
                new OneTimeTask("Химчистка", "Сдать дубенку в химчистку", LocalDateTime.of(2023, 2, 2, 20, 30), Type.PERSONAL),
                new OneTimeTask("Поручение", "Исполнить поручение руководителя", LocalDateTime.of(2023, 2, 3, 8, 0), Type.WORK),
                new OneTimeTask("Конференция", "Сделать доклад на конференции", LocalDateTime.of(2023, 2, 3, 8, 0), Type.WORK)
        };

// Заносим задачи в ежедневник с присвоением id
        System.out.println("1.Добавляем задачи в ежедневник");
        for (DailyTask task : dailyTasks) {
            TaskService.addTask(task);
        }

        for (WeeklyTask task : weeklyTasks) {
            TaskService.addTask(task);
        }

        for (MonthlyTask task : monthlyTasks) {
            TaskService.addTask(task);
        }

        for (YearlyTask task : yearlyTasks) {
            TaskService.addTask(task);
        }

        for (OneTimeTask task : oneTimeTasks) {
            TaskService.addTask(task);
        }

        // Получаем список задач на предстоящий день
        System.out.println("\n2. Получаем список задач на предстоящий день");
        TaskService.getAllByDate(LocalDate.now().plusDays(1));

        // Удаляем задачи из ежедневника по номеру
        // Список задач на день перед удалением
        System.out.println("\n3. Удаляем задачи из ежедневника по номеру. Список перед удалением");
        TaskService.getAllByDate(LocalDate.of(2023, 1, 27));

        TaskService.remove(9);

        // Список задач после удаления
        System.out.println("\n Список после удаления");
        TaskService.getAllByDate(LocalDate.of(2023, 1, 27));

        // Редактируем заголовок задачи
        System.out.println("\n Редактируем заголовок задачи");
        TaskService.updateTitle(10, "Задание на неделю");

        // Редактируем описание задачи
        System.out.println("\n Редактируем описание задачи");
        TaskService.updateDescription(10, " Обсудить с заказчиком работы на неделю");

        // Получаем все задачи, сгруппированные по датам
        System.out.println("\n Получаем все задачи, сгруппированные по датам");
        TaskService.getAllGroupeByDate();

        System.out.println("\n Работаем с ежедневником через меню ");
        Menu.SelectMenu();
    }
}