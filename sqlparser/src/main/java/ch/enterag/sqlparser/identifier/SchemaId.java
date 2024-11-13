package ch.enterag.sqlparser.identifier;

import ch.enterag.sqlparser.SqlLiterals;
import java.text.ParseException;

public class SchemaId {
   private String _sCatalog;
   private String _sSchema;

   public String getCatalog() {
      return this._sCatalog;
   }

   public void setCatalog(String sCatalog) {
      this._sCatalog = sCatalog;
   }

   public void parseCatalog(String sCatalogDelimited) throws ParseException {
      this.setCatalog(SqlLiterals.parseId(sCatalogDelimited));
   }

   public String getSchema() {
      return this._sSchema;
   }

   public void setSchema(String sSchema) {
      this._sSchema = sSchema;
   }

   public void parseSchema(String sSchemaDelimited) throws ParseException {
      this.setSchema(SqlLiterals.parseId(sSchemaDelimited));
   }

   public String quote() {
      return SqlLiterals.quoteQualifiedSchema(this.getCatalog(), this.getSchema());
   }

   public String format() {
      return SqlLiterals.formatQualifiedSchema(this.getCatalog(), this.getSchema());
   }

   public boolean isSet() {
      return this._sSchema != null;
   }

   public SchemaId() {
   }

   public SchemaId(String sCatalog, String sSchema) {
      this.setCatalog(sCatalog);
      this.setSchema(sSchema);
   }
}
