package dev.brighten.bot.data.sql;

import java.sql.ResultSet;

public interface ResultSetIterator {
    void next(ResultSet rs) throws Exception;
}
