package ch.admin.bar.siard2.tibero;

import ch.admin.bar.siard2.jdbc.OracleConnection;
import ch.admin.bar.siard2.jdbc.TiberoConnection;
import ch.admin.bar.siard2.oracle.datatype.OracleDataType;
import ch.admin.bar.siard2.oracle.datatype.OraclePredefinedType;
import ch.admin.bar.siard2.oracle.ddl.*;
import ch.admin.bar.siard2.oracle.dml.OracleInsertStatement;
import ch.admin.bar.siard2.oracle.expression.*;
import ch.admin.bar.siard2.tibero.datatype.TiberoDataType;
import ch.enterag.sqlparser.datatype.DataType;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.ddl.*;
import ch.enterag.sqlparser.dml.InsertStatement;
import ch.enterag.sqlparser.expression.*;

public class TiberoSqlFactory {
    private TiberoConnection _tiberoConn = null;

    public TiberoSqlFactory() {

    }

    public void setConnection(TiberoConnection tiberoConn) { this._tiberoConn = tiberoConn; }

    public TiberoConnection getConnection() {
        return this._tiberoConn;
    }

    public DataType newDataType() {
        return new TiberoDataType(this);
    }

    public Literal newLiteral() {
        return new TiberoLiteral(this);
    }

    public UnsignedLiteral newUnsignedLiteral() {
        return new OracleUnsignedLiteral(this);
    }

    public PredefinedType newPredefinedType() {
        return new OraclePredefinedType(this);
    }

    public CreateSchemaStatement newCreateSchemaStatement() {
        return new OracleCreateSchemaStatement(this);
    }

    public DropSchemaStatement newDropSchemaStatement() {
        return new OracleDropSchemaStatement(this);
    }

    public DropTableStatement newDropTableStatement() {
        return new OracleDropTableStatement(this);
    }

    public DropViewStatement newDropViewStatement() {
        return new OracleDropViewStatement(this);
    }

    public CreateTypeStatement newCreateTypeStatement() {
        return new OracleCreateTypeStatement(this);
    }

    public AttributeDefinition newAttributeDefinition() {
        return new OracleAttributeDefinition(this);
    }

    public MethodSpecification newMethodSpecification() {
        return new OracleMethodSpecification(this);
    }

    public PartialMethodSpecification newPartialMethodSpecification() {
        return new OraclePartialMethodSpecification(this);
    }

    public CreateFunctionStatement newCreateFunctionStatement() {
        return new OracleCreateFunctionStatement(this);
    }

    public CreateProcedureStatement newCreateProcedureStatement() {
        return new OracleCreateProcedureStatement(this);
    }

    public SqlParameterDeclaration newSqlParameterDeclaration() {
        return new OracleSqlParameterDeclaration(this);
    }

    public ReturnsClause newReturnsClause() {
        return new OracleReturnsClause(this);
    }

    public DropTypeStatement newDropTypeStatement() {
        return new OracleDropTypeStatement(this);
    }

    public InsertStatement newInsertStatement() {
        return new OracleInsertStatement(this);
    }

    public QueryExpressionBody newQueryExpressionBody() {
        return new OracleQueryExpressionBody(this);
    }

    public ValueExpressionPrimary newValueExpressionPrimary() {
        return new OracleValueExpressionPrimary(this);
    }

    public NumericValueFunction newNumericValueFunction() {
        return new OracleNumericValueFunction(this);
    }

}
