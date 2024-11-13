package ch.enterag.sqlparser.identifier;

import ch.enterag.sqlparser.SqlLiterals;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QualifiedId {
   private String _sCatalog = null;
   private String _sSchema = null;
   private String _sName = null;

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

   public String getName() {
      return this._sName;
   }

   public void setName(String sName) {
      this._sName = sName;
   }

   public void parseName(String sNameDelimited) throws ParseException {
      this.setName(SqlLiterals.parseId(sNameDelimited));
   }

   public String quote() {
      return SqlLiterals.quoteQualifiedName(this.getCatalog(), this.getSchema(), this.getName());
   }

   public String format() {
      return SqlLiterals.formatQualifiedName(this.getCatalog(), this.getSchema(), this.getName());
   }

   public boolean isSet() {
      return this._sName != null;
   }

   public boolean equals(Object o) {
      boolean bEqual = false;
      if (o instanceof QualifiedId) {
         QualifiedId qi = (QualifiedId)o;
         if (qi != null) {
            bEqual = Objects.equals(this.getName(), qi.getName()) && Objects.equals(this.getSchema(), qi.getSchema()) && Objects.equals(this.getCatalog(), qi.getCatalog());
         }
      }

      return bEqual;
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.getName(), this.getSchema(), this.getCatalog()});
   }

   public QualifiedId() {
   }

   public QualifiedId(String sCatalog, String sSchema, String sName) {
      this.setCatalog(sCatalog);
      this.setSchema(sSchema);
      this.setName(sName);
   }

   public QualifiedId(String sFormatted) throws ParseException {
      if (sFormatted != null) {
         List<String> listIds = new ArrayList();

         int iEnd;
         for(int iStart = 0; iStart < sFormatted.length(); iStart = iEnd + 1) {
            iEnd = SqlLiterals.getIdentifierEnd(sFormatted, iStart);
            listIds.add(SqlLiterals.parseId(sFormatted.substring(iStart, iEnd)));
            if (iEnd < sFormatted.length() && !sFormatted.substring(iEnd).startsWith(".")) {
               throw new ParseException("Qualified identifier must be separated by periods!", 0);
            }
         }

         if (listIds.size() > 0) {
            this.setName((String)listIds.get(listIds.size() - 1));
         }

         if (listIds.size() > 1) {
            this.setSchema((String)listIds.get(listIds.size() - 2));
         }

         if (listIds.size() > 2) {
            this.setCatalog((String)listIds.get(listIds.size() - 3));
         }
      }

   }
}
