/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpledb.query;

import simpledb.record.Schema;

/**
 *
 * @author rynehanson
 */
public class AntijoinPlan {
    private Plan p1, p2, p3;
    private Predicate pred;
    private Schema schema = new Schema();
    
    public AntijoinPlan(Plan p1, Plan p2, Plan p3, Predicate pred) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.pred = pred;
        schema.addAll(p1.schema());
        schema.addAll(p2.schema());
        schema.addAll(p3.schema()); 
    }
    
    public Scan open() {
      Scan s1 = p1.open();
      Scan s2 = p2.open();
      Scan s3 = p3.open();
      return new AntijoinScan(s1, s2, s3, this.pred);
    }
   
    public int blocksAccessed() {
      return p1.blocksAccessed() + (p1.recordsOutput() * p2.blocksAccessed());
   }
    
   public int recordsOutput() {
      return p1.recordsOutput() * p2.recordsOutput();
   }
   
   public int distinctValues(String fldname) {
      if (p1.schema().hasField(fldname))
         return p1.distinctValues(fldname);
      else
         return p2.distinctValues(fldname);
   }
   
   public Schema schema() {
      return schema;
   }
}
