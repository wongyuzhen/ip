# Jane Chatbot

![Jane Chatbot](Ui.png)

Jane is a lightweight task manager available via CLI and a simple GUI. It supports todos, deadlines, events, search, marking, deletion, and a reminder command that lists tasks occurring within the next N weeks.

## List of features

1. Add: todo, deadline, event
2. List tasks (1-based numbering)
3. Find by keyword
4. Mark / unmark
5. Delete
6. Remind for tasks within a time window (next N weeks)
7. Persistent storage to a text file

## 1. Add Todo

Action: Add a simple task without a date.

Usage: `todo DESCRIPTION`

Example: `todo buy milk`

Outcome: 
```
KABOOM! A wild task has appeared:
[T][ ] buy milk
You are now juggling 1 thingamajig. Try not to drop them!
```

## 2. Add Deadline

Action: Add a task that must be completed by a certain date/time.

Usage: `deadline DESCRIPTION /by DATE`

Example: `deadline CS1008 assignment /by 2025-09-30`

Outcome:
```
KABOOM! A wild task has appeared:
[D][ ] CS1008 assignment (by: Sep 30 2025)
You are now juggling 2 thingamajigs. Try not to drop them!
```

## 3. Add Event

Action: Add a task with start and end date/time.

Usage: `event DESCRIPTION /from DATE TIME /to DATE TIME`

Example: `event hackathon /from 2025-10-10 09:00 /to 2025-10-11 18:00`

Outcome:
```
KABOOM! A wild task has appeared:
[E][ ] hackathon (from: Oct 10 2025, 9:00 AM to: Oct 11 2025, 6:00 PM)
You are now juggling 3 thingamajigs. Try not to drop them!
```

## Date & Time Formats

### Accepted inputs:

### Dates
1. yyyy-MM-dd (e.g., 2019-10-15)
2. d/M/yyyy (e.g., 2/12/2019)

### Times
1. HHmm (e.g., 1800)
2. HH:mm (e.g., 18:00)

### DateTime
1. yyyy-MM-dd HHmm (e.g., 2019-10-15 1800)
2. yyyy-MM-dd HH:mm (e.g., 2019-10-15 18:00)
3. d/M/yyyy HHmm (e.g., 2/12/2019 1800)
4. d/M/yyyy HH:mm (e.g., 2/12/2019 18:00)

### Display formats:
1. Date: MMM dd yyyy (e.g., Oct 15 2019)
2. DateTime: MMM dd yyyy, h:mm a (e.g., Oct 15 2019, 6:00 PM)

### Storage formats:
1. Date: yyyy-MM-dd
2. DateTime: yyyy-MM-dd HHmm

## 4. List Tasks

Action: Show all tasks in your list.

Usage: `list`

Example: `list`

Outcome:
```
Here comes your legendary scroll of chores:
1. [T][ ] buy milk
2. [D][ ] CS1008 assignment (by: Sep 30 2025, 11:59 PM)
3. [E][ ] hackathon (from: Oct 10 2025, 9:00 AM to: Oct 11 2025, 6:00 PM)
```

## 5. Find Tasks

Action: Search for tasks containing a keyword.

Usage: `find KEYWORD`

Example: `find milk`

Outcome:
```
Ta-da! Here are the shiny matches I dug up:
1. [T][ ] buy milk
```

## 6. Mark Task

Action: Mark a task as done.

Usage: `mark INDEX`

Example: `mark 1`

Outcome:
```
VICTORY! Task obliterated and marked complete:
[T][X] buy milk
```

## 7. Unmark Task

Action: Mark a task as not done.

Usage: `unmark INDEX`

Example: `unmark 1`

Outcome:
```
Plot twist! Task resurrected and marked incomplete:
[T][ ] buy milk
```

## 8. Delete Task

Action: Remove a task permanently.

Usage: `delete INDEX`

Example: `delete 2`

Outcome:
```
ZAP! A task has been banished into the void:
[D][ ] CS1008 assignment (by: Sep 30 2025, 11:59 PM)
Remaining loot: 2 quests. Choose wisely.
```

## 9. Reminders

Action: Show tasks happening within the next N weeks (default 1).

Usage: `remindme /[NUMBER OF WEEKS] weeks`

Example: `remindme /2 weeks`

Outcome:
```
Attention! Time wizard Jane has spoken.
Between 2025-09-14 17:00 and 2025-09-28 17:00, here’s what destiny holds:
1. [D][ ] CS1008 assignment (by: Sep 30 2025, 11:59 PM)
```

## 10. Exit

Action: Exit JaneBot.

Usage: `bye`

Example: `bye`

Outcome:
```
*Jane does a dramatic stage exit*
Farewell, brave adventurer. May your snacks be plentiful.
```