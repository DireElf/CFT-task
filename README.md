﻿Java 7, сборки проекта не использовались

Сортировка слиянием

Что реализовано:
- обработка аргументов командной строки (корректность ввода проверяется)
- чтение из файлов (строки с ошибками игнорируются)
- сортировка слиянием чисел либо текста в прямом либо обратном порядке независимо от количества файлов
- запись отсортированных данных в файл

Что не реализовано:
- проверка порядка сортировки данных (и ошибок порядка сортировки) во входных файлах
- обработка больших файлов 

1. Входные данные

Программа принимает данные в текстовых файлах с раширением .txt, количество файлов - не менее одного, иначе выдается сообщение об ошибке.
Данные должны быть записаны в столбик (каждая строка новый элемент).
Данные в файлах по умолчанию считаются отсортированными в возрастающем порядке.
Если указано, что данные представлены в числовом формате, пробельные и другие ошибочные строки игнорируются.
Также игнорируются числа вне диапазона восьмибайтовых знаковых целых чисел (-2147483648 до 2147483647).
Если данные представлены в текстовом формате, игнорируются пробельные строки.

2. Аргументы командной строки

Аргументы командной строки должны иметь формат вида -a -i out.txt in1.txt in2.txt in3.txt ...,
и состоять минимум из трёх элементов.
Первый аргумент (порядок сортировки) может принимать значения -а (требуется сортировка в возрастающем порядке),
и -d (требуется сортировка в убывающем порядке).
Если первый аргумент пропущен, это считается указанием на сортировку по возрастанию
Второй аргумент (тип данных) может принимать значения -i (в файлах представлены числовые данные), либо
-s (файлы содержат текстовые данные). Это обязательный аргумент.
За указанием на порядок сортировки и тип данных в командной строке должны следовать строковые параметры, 
содержащие абсолютные пути к файлам (адреса файлов).
Первым адресом должен являться путь к выходному файлу (обязательный аргумент).
Выходной файл должен существовать.
Далее должны быть перечислены адреса входных файлов (не менее одного) - обязательный аргумент.
При нарушении указанных способов ввода аргументов программа сообщает об ошибке.