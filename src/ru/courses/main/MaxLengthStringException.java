package ru.courses.main;

public class MaxLengthStringException extends IllegalArgumentException {
    public MaxLengthStringException(int CHECK_MAX_VALUE, int length) {
        super("Строка не должна привышать длину " + CHECK_MAX_VALUE + " символа. Текущая длина : " + length);
    }
}
