package fr.ynov.collection.dialect;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();
    }

    public String getTypeName(int code) {
        switch (code) {
            case java.sql.Types.BOOLEAN:
                return "boolean";
            case java.sql.Types.TINYINT:
                return "tinyint";
            case java.sql.Types.SMALLINT:
                return "smallint";
            case java.sql.Types.INTEGER:
                return "integer";
            case java.sql.Types.BIGINT:
                return "bigint";
            case java.sql.Types.FLOAT:
                return "float";
            case java.sql.Types.DOUBLE:
                return "double";
            case java.sql.Types.NUMERIC:
                return "numeric";
            case java.sql.Types.DECIMAL:
                return "decimal";
            case java.sql.Types.CHAR:
                return "char";
            case java.sql.Types.VARCHAR:
                return "varchar";
            case java.sql.Types.LONGVARCHAR:
                return "longvarchar";
            case java.sql.Types.DATE:
                return "date";
            case java.sql.Types.TIME:
                return "time";
            case java.sql.Types.TIMESTAMP:
                return "timestamp";
            case java.sql.Types.BINARY:
            case java.sql.Types.VARBINARY:
            case java.sql.Types.LONGVARBINARY:
                return "blob";
            default:
                return "text";
        }
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport();
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }
}
