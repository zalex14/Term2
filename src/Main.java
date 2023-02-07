import diary.Type;
import diary.exception.IncorrectArgumentException;
import diary.exception.TaskNotFoundException;
import diary.task.*;
import diary.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import word.WordStat;

/**
 * Сервис управления задачами по типу ежедневника
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(" \nКурсовая работа «Java Core» \nСервис управления задачами по типу ежедневника\n ");

// Ввод данных явно через переменные
        DailyTask[] dailyTasks;
        WeeklyTask[] weeklyTasks;
        MonthlyTask[] monthlyTasks;
        YearlyTask[] yearlyTasks;
        OneTimeTask[] oneTimeTasks;

        try {
            dailyTasks = new DailyTask[]{
                    new DailyTask("Пробуждение", "Пробуждение и подьем", LocalDateTime.of(2023, 1, 27, 7, 0), Type.PERSONAL),
                    new DailyTask("Зарядка", "Зарядка, умывание, закаливающие процедуры", LocalDateTime.of(2023, 1, 27, 7, 30), Type.PERSONAL),
                    new DailyTask("Завтрак", "Сварить кашу, заварить какао", LocalDateTime.of(2023, 1, 27, 8, 0), Type.PERSONAL),
                    new DailyTask("Работа", "Приступить к работе", LocalDateTime.of(2023, 1, 27, 9, 0), Type.WORK),
                    new DailyTask("Ужин", "Поужинать", LocalDateTime.of(2023, 1, 27, 19, 0), Type.PERSONAL),
                    new DailyTask("Сон", "Ложиться спать", LocalDateTime.of(2023, 1, 27, 23, 0), Type.PERSONAL)
            };

            weeklyTasks = new WeeklyTask[]{
                    new WeeklyTask("Магазин", "Сьездить за продуктами на неделю", LocalDateTime.of(2023, 2, 5, 7, 0), Type.PERSONAL),
                    new WeeklyTask("Планерка", "Посетить еженедельную планерку", LocalDateTime.of(2023, 1, 31, 7, 30), Type.WORK),
                    new WeeklyTask("Уборка", "Помыть пол и сделать уборку в квартире", LocalDateTime.of(2023, 1, 28, 8, 0), Type.PERSONAL)
            };

            monthlyTasks = new MonthlyTask[]{
                    new MonthlyTask("Квартплата", "Оплатить услуги ЖКХ", LocalDateTime.of(2023, 1, 28, 7, 0), Type.PERSONAL),
                    new MonthlyTask("Месячное задание", "Согласовать месячное задание", LocalDateTime.of(2022, 1, 27, 9, 0), Type.WORK),
                    new MonthlyTask("Месячный отчет", "Подготовить ежемесячный отчет", LocalDateTime.of(2023, 1, 26, 18, 0), Type.WORK)
            };

            yearlyTasks = new YearlyTask[]{
                    new YearlyTask("Новый год", "Встретить Новый год", LocalDateTime.of(2022, 1, 1, 0, 0), Type.PERSONAL),
                    new YearlyTask("Отпуск", "Написать заявление на отпуск", LocalDateTime.of(2024, 7, 1, 9, 0), Type.WORK),
                    new YearlyTask("Получить 13 зарплату", "Сходить в кассу за ежегодной премией", LocalDateTime.of(2023, 12, 30, 10, 30), Type.WORK)
            };

            oneTimeTasks = new OneTimeTask[]{
                    new OneTimeTask("Кинотеатр", "Заказать билеты в кино", LocalDateTime.of(2023, 2, 3, 20, 0), Type.PERSONAL),
                    new OneTimeTask("Химчистка", "Сдать дубенку в химчистку", LocalDateTime.of(2023, 2, 4, 20, 30), Type.PERSONAL),
                    new OneTimeTask("Поручение", "Исполнить поручение руководителя", LocalDateTime.of(2023, 2, 5, 8, 0), Type.WORK),
                    new OneTimeTask("Конференция", "Сделать доклад на конференции", LocalDateTime.of(2023, 2, 6, 8, 0), Type.WORK)
            };
        } catch (IncorrectArgumentException e) {
            throw new RuntimeException(e);
        }


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
        System.out.println(TaskService.getAllByDate(LocalDate.now().plusDays(1)));

        // Удаляем задачи из ежедневника по номеру
        System.out.println("\n3. Удаляем задачи из ежедневника по номеру.");
        System.out.println(" Список перед удалением \n" + TaskService.getAllByDate(LocalDate.of(2023, 2, 6)));

        try {
            TaskService.remove(12);
        } catch (TaskNotFoundException e) {
            System.out.println(" Задача не удалена ");
        }

        System.out.println("\n Список после удаления \n" + TaskService.getAllByDate(LocalDate.of(2023, 2, 6)));

        // Редактируем заголовок задачи
        System.out.println(TaskService.getRemovedTasks());

        // Редактируем заголовок задачи
        System.out.println("\n ii Редактируем заголовок задачи");
        try {
            TaskService.updateTitle(11, "Задание на неделю новое");
        } catch (TaskNotFoundException | IncorrectArgumentException e) {
            System.out.println(" Заголовок задачи не изменен ");
        }

        // Редактируем описание задачи
        System.out.println("\n ii Редактируем описание задачи");
        try {
            TaskService.updateDescription(11, " Обсудить с заказчиком работы на неделю");
        } catch (TaskNotFoundException | IncorrectArgumentException e) {
            System.out.println(" Описание задачи не изменено ");
        }

        System.out.println(TaskService.getAllByDate(LocalDate.now().plusDays(1)));

        // Получаем все задачи, сгруппированные по датам
        System.out.println("\n ii Получаем все задачи, сгруппированные по датам");
        TaskService.getAllGroupeByDate();

        System.out.println("\n i Работаем с ежедневником через меню ");
        Menu.selectMenu();

        System.out.println("iii Приложение, которое на вход через консоль, получит текст и выдаст статистику. Введите текст предложения");
        Scanner sc = new Scanner(System.in);
        String phrase = sc.nextLine();
//        sentence ="yourapp the quick brown fox fox jumps over the lazy dog";
        WordStat.wordStat(phrase);
    }
}