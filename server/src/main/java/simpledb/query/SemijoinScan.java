/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpledb.query;

import simpledb.record.*;
/**
 *
 * @author rynehanson
 */
public class SemijoinScan implements Scan {
   private Scan prod;
   private Predicate pred;
   
   /**
    * Creates a select scan having the specified underlying
    * scan and predicate.
    * @param s the scan of the underlying query
    * @param pred the selection predicate
    */
   public SemijoinScan(Scan s1, Scan s2, Predicate pred) {
      this.prod = new ProductScan(s1, s2);
      this.pred = pred;
      s1.next();
   }
   
   // Scan methods
   
   public void beforeFirst() {
      prod.beforeFirst();
   }
   
   /**
    * Move to the next record satisfying the predicate.
    * The method repeatedly calls next on the underlying scan
    * until a suitable record is found, or the underlying scan
    * contains no more records.
    * @see simpledb.query.Scan#next()
    */
   public boolean next() {
      while (prod.next())
         if (pred.isSatisfied(prod))
         return true;
      return false;
   }
   
   public void close() {
      prod.close();
   }
   
   public Constant getVal(String fldname) {
      return prod.getVal(fldname);
   }
   
   public int getInt(String fldname) {
      return prod.getInt(fldname);
   }
   
   public String getString(String fldname) {
      return prod.getString(fldname);
   }
   
   public boolean hasField(String fldname) {
      return prod.hasField(fldname);
   }
}