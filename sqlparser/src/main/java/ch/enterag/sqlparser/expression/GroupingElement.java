package ch.enterag.sqlparser.expression;

import ch.enterag.sqlparser.K;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.antlr4.EnhancedSqlBaseVisitor;
import ch.enterag.sqlparser.antlr4.SqlBase;
import ch.enterag.sqlparser.generated.SqlParser;
import ch.enterag.sqlparser.identifier.IdChain;
import ch.enterag.utils.logging.IndentLogger;
import java.util.ArrayList;
import java.util.List;

public class GroupingElement extends SqlBase {
   private static IndentLogger _il = IndentLogger.getIndentLogger(GroupingElement.class.getName());
   private GroupingElement.GeVisitor _visitor = new GroupingElement.GeVisitor();
   private List<IdChain> _listOrdinaryGroupingSets = new ArrayList();
   private boolean _bRollup = false;
   private boolean _bCube = false;
   private boolean _bGroupingSets = false;
   private List<GroupingElement> _listGroupingElements = new ArrayList();

   private GroupingElement.GeVisitor getVisitor() {
      return this._visitor;
   }

   public List<IdChain> getOrdinaryGroupingSets() {
      return this._listOrdinaryGroupingSets;
   }

   private void setOrdinaryGroupingSets(List<IdChain> listOrdinaryGroupingSets) {
      this._listOrdinaryGroupingSets = listOrdinaryGroupingSets;
   }

   public boolean isRollup() {
      return this._bRollup;
   }

   public void setRollup(boolean bRollup) {
      this._bRollup = bRollup;
   }

   public boolean isCube() {
      return this._bCube;
   }

   public void setCube(boolean bCube) {
      this._bCube = bCube;
   }

   public boolean isGroupingSets() {
      return this._bGroupingSets;
   }

   public void setGroupingSets(boolean bGroupingSets) {
      this._bGroupingSets = bGroupingSets;
   }

   public List<GroupingElement> getGroupingElements() {
      return this._listGroupingElements;
   }

   private void setGroupingElements(List<GroupingElement> listGroupingElements) {
      this._listGroupingElements = listGroupingElements;
   }

   public String format() {
      String s = null;
      int i;
      if (!this.isRollup() && !this.isCube()) {
         if (this.isGroupingSets()) {
            s = K.GROUPING.getKeyword() + " " + K.SETS.getKeyword() + "(";

            for(i = 0; i < this.getGroupingElements().size(); ++i) {
               if (i > 0) {
                  s = s + "," + " ";
               }

               s = s + ((GroupingElement)this.getGroupingElements().get(i)).format();
            }

            s = s + ")";
         } else if (this.getOrdinaryGroupingSets().size() == 1) {
            s = ((IdChain)this.getOrdinaryGroupingSets().get(0)).format();
         } else {
            s = "(";

            for(i = 0; i < this.getOrdinaryGroupingSets().size(); ++i) {
               if (i > 0) {
                  s = s + "," + " ";
               }

               s = s + ((IdChain)this.getOrdinaryGroupingSets().get(i)).format();
            }

            s = s + ")";
         }
      } else {
         if (this.isRollup()) {
            s = K.ROLLUP.getKeyword();
         } else if (this.isCube()) {
            s = K.CUBE.getKeyword();
         }

         s = s + "(";

         for(i = 0; i < this.getOrdinaryGroupingSets().size(); ++i) {
            if (i > 0) {
               s = s + "," + " ";
            }

            s = s + ((IdChain)this.getOrdinaryGroupingSets().get(i)).format();
         }

         s = s + ")";
      }

      return s;
   }

   public void parse(SqlParser.GroupingElementContext ctx) {
      this.setContext(ctx);
      this.getVisitor().visit(this.getContext());
   }

   public void parse(String sSql) {
      this.setParser(newSqlParser(sSql));
      this.parse(this.getParser().groupingElement());
   }

   public void initialize(List<IdChain> listOrdinaryGroupingSets, boolean bRollup, boolean bCube, boolean bGroupingSets, List<GroupingElement> listGroupingElements) {
      _il.enter(new Object[0]);
      this.setOrdinaryGroupingSets(listOrdinaryGroupingSets);
      this.setRollup(bRollup);
      this.setCube(bCube);
      this.setGroupingElements(listGroupingElements);
      _il.exit();
   }

   public GroupingElement(SqlFactory sf) {
      super(sf);
   }

   private class GeVisitor extends EnhancedSqlBaseVisitor<GroupingElement> {
      private GeVisitor() {
      }

      public GroupingElement visitGroupingElement(SqlParser.GroupingElementContext ctx) {
         int i;
         if (ctx.ordinaryGroupingSet() != null) {
            for(i = 0; i < ctx.ordinaryGroupingSet().identifierChain().size(); ++i) {
               IdChain icGroupingSet = new IdChain();
               this.setIdChain(ctx.ordinaryGroupingSet().identifierChain(i), icGroupingSet);
               GroupingElement.this.getOrdinaryGroupingSets().add(icGroupingSet);
            }
         }

         if (ctx.ROLLUP() != null) {
            GroupingElement.this.setRollup(true);
         } else if (ctx.CUBE() != null) {
            GroupingElement.this.setCube(true);
         } else if (ctx.GROUPING() != null && ctx.SETS() != null) {
            GroupingElement.this.setGroupingSets(true);

            for(i = 0; i < ctx.groupingElement().size(); ++i) {
               GroupingElement ge = GroupingElement.this.getSqlFactory().newGroupingElement();
               ge.parse(ctx.groupingElement(i));
               GroupingElement.this.getGroupingElements().add(ge);
            }
         }

         return GroupingElement.this;
      }

      // $FF: synthetic method
      GeVisitor(Object x1) {
         this();
      }
   }
}
