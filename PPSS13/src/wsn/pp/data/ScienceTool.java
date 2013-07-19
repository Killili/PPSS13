/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import wsn.pp.filter.LinkInfo;
import wsn.pp.filter.LinkKNN;

/**
 *
 * @author Marvin
 */
public class ScienceTool {
   public static HashMap<String,HashMap<Long,HashMap<String,Object>>> informations = new HashMap<String, HashMap<Long, HashMap<String, Object>>>();
   public static HashMap<String,Double> parameters;
   public static DefaultComboBoxModel parameterModel;
   public static DefaultComboBoxModel metaDataModel;
   
   public static final boolean _SCIENCE = true;
   public static final boolean _HEAVY_SCIENCE = true; //takes lootsss of ram
   public static final boolean _SPLIT_VALUES = true; //take part of the log only
    private static LinkedList<String> labelsSave = new LinkedList<String>();
    private static HashMap<String,Float> lastSystemTestPoints;
    private static HashMap<String,HashMap<String, Float>> lastSystemTestScore;
    private static HashMap<String, HashMap<String, Integer>> systemStateChangeds;
    private static float state;
   
   
   public static void clearAll()
   {
       informations = new HashMap<String, HashMap<Long, HashMap<String, Object>>>();
       labelsSave = null;
   }
   
   public static void addLinkInfor(LinkInfo li)
   {
       if(!_HEAVY_SCIENCE || (li.getDestinationNode() != 1||li.getSourceNode()!=2))
           return;
       
       if(labelsSave==null)
           labelsSave = new LinkedList<String>();
       splitUpMetaInfo(li);      
       
       String name = ""+li.getSourceNode()+"-"+li.getDestinationNode();
       if(!informations.containsKey(name))
           informations.put(name, new HashMap<Long,HashMap<String,Object>>());
       if(!informations.get(name).containsKey(li.getTimestamp()))
           informations.get(name).put(li.getTimestamp(), new HashMap<String, Object>());
       
       for(Map.Entry<String, Object> s:li.getMetaData().entrySet())
       {
           //System.out.println("Read "+ name + "ts:"+li.getTimestamp() +"s.key:"+s.getKey() + " s.value:"+s.getValue());
           if(s.getValue()==null)
               continue;
           informations.get(name).get(li.getTimestamp()).put(s.getKey(),s.getValue().toString());
       }
       
   }
   
   public static void splitUpMetaInfo(LinkInfo li)
   {
       li.getMetaData().put("power",li.getPower());
       li.getMetaData().put("time", li.getTimestamp());
       li.getMetaData().put("source", li.getSourceNode());
       li.getMetaData().put("destination", li.getDestinationNode());
       
       if(li.getMetaData().containsKey("Neighbores"))
       {
        
           int i = 1;
           for(LinkKNN.Neighbores n:(List<LinkKNN.Neighbores>)li.getMetaData().get("Neighbores"))
           {
           //li.getMetaData().remove("Neighbores");
           li.getMetaData().put("Neighbore("+i+") Distance", n.distance);
           
           LinkKNN.DataPoint d = n.data;
           li.getMetaData().put("Neighbore("+i+") Type", d.Type);
           li.getMetaData().put("Neighbore("+i+") X", d.position.x);
           li.getMetaData().put("Neighbore("+i+") Y", d.position.y);
           i++;
           }
           
       }
       
       if(li.getMetaData().containsKey("Datapoint"))
       {
           //li.getMetaData().remove("Datapoint");
           LinkKNN.DataPoint d = (LinkKNN.DataPoint)li.getMetaData().get("Datapoint");
           if(d!=null)
           {
           li.getMetaData().put("Datapoint Type", d.Type);
           li.getMetaData().put("Datapoint X", d.position.x);
           li.getMetaData().put("Datapoint Y", d.position.y);
           }
       }
       for(String t:li.getMetaData().keySet())
           if(!labelsSave.contains(t))
                    labelsSave.add(t);
       updateMetaData();
   }
   
   
   public static void addInfo(int source,int target,long timestamp,String label,String value)
   {
       
       String name = ""+source+"-"+target;
          
       if(!informations.containsKey(name))
           informations.put(name, new HashMap<Long,HashMap<String,Object>>());
       
       if(!informations.get(name).containsKey(timestamp))
           informations.get(name).put(timestamp, new HashMap<String, Object>());
       
       informations.get(name).get(timestamp).put(label,value);
   }
   
   public static String getParameterValueList()
   {
       checkParameterList();
       String out = "";
       for(Map.Entry<String, Double> s:parameters.entrySet())
       {
           out+=s.getKey()+":"+s.getValue()+"\t";
       }
       return out;
   }
   
   public static void saveAll(String fileName)
   {
       int i = 1;
       for(String s:informations.keySet())
       {
           System.out.println("Saving file "+i+"/"+informations.keySet().size());
           saveLink(s,"logs/"+fileName+"("+getParameterValueList().replace("\t", "-")+")/"+s+".txt",null);
           i++;
       }
   }
   
   /**
    * link = <source>-<destination>
    * @param link 
    */
   public static void saveLink(String link,String fileName,LinkedList<String> toPlot)
   {
       System.out.println("Saving link "+link);
       HashMap<Long, HashMap<String, Object>> values = informations.get(link);
       String output;
       output = getParameterValueList()+"\n";

       
       boolean header = false;
       LinkedList<String> labels = new LinkedList<String>();
       
       
       if(toPlot!=null)
            output +=getParameterLinked(toPlot);
       int i = 0;
       for(HashMap<String, Object> tv:values.values())
       {
           i++;
           if(i>=getParameter("PlotAmount"))
               break;
           if(toPlot!=null)
           {
               header = true;
               for(String s:toPlot)
                   if(!labels.contains(s))
                        labels.add(s);
           }
           
           if(!header)
           {
               for(String t :tv.keySet())
               {
                   if(!labels.contains(t))
                    labels.add(t);
                   output += t +"\t";
               }
               output+="\n";
               header = true;
           }
           
           for(String t :labels)
           {
               Object value = tv.get(t);
               if(value == null)
                   value = "not set";
               else
                   
                   
                       
                   if(!t.equals("time"))
                   {
                   try
                   {
                       value = Double.parseDouble((String)value);
                       
                       value = 0.001*(int)(1000*((Double)value));
                       value = Double.toString((Double)value).replace(".", ",");
                   }
                    catch(Exception e){};
                   }
                   
                    
                   output += value +"\t";
           }
               output+="\n";
       }
       
       File f = new File(fileName);
      
       try {
           if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
           
           if (!f.exists())
                f.createNewFile();
           FileWriter w = new FileWriter(f);
           w.write(output);
           w.flush();
           w.close();
       } catch (IOException ex) {
           Logger.getLogger(ScienceTool.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   }
   
    
   
   public static double getParameter(String name)
   {
       checkParameterList();
       if(parameters.containsKey(name))
        return parameters.get(name);
       else
           System.out.println("Could not find parameters for Key:"+ name);
       return -1;
   }
   
   public static void setParameter(String name,double value)
   {
       checkParameterList();
       parameters.put(name, value);
   }
   
   private static void checkParameterList()
   {
       if(parameters == null)
       {
           parameters = new HashMap<String, Double>();
           parameters.put("knn", 5.0);
           parameters.put("window", 30.0);
           parameters.put("alpha", 0.2);
           parameters.put("trustThreshold", 0.1);
           parameters.put("PlotAmount", 500.);
           parameters.put("KnnWindow", 100.);
           parameters.put("Slittpart", 0.);
       }
       
   }

   public static ComboBoxModel getAllMetadata()
   {
       updateMetaData();
       return metaDataModel;
   }
   
   private static void updateMetaData()
   {
           if(metaDataModel==null)
               metaDataModel = new DefaultComboBoxModel();
           
           metaDataModel.removeAllElements();
           if(labelsSave !=null)
            for(String s:labelsSave)
                metaDataModel.addElement(s);       
   }
   
    public static ComboBoxModel getAllParameters() {
        if(parameterModel==null)
        {
            checkParameterList();
            parameterModel = new DefaultComboBoxModel();
            for(String v:parameters.keySet())
            {
                if(v.equals("Neighbore")|| v.equals("Datapoint"))
                    continue;
                parameterModel.addElement(v);
            }
       }
        System.out.println(parameterModel.getSize());
        return parameterModel;
    }

    public static void saveAll(String fileName, LinkedList<String> toPlot) {
        int i=0;
        for(String s:informations.keySet())
        {
            i++;
            System.out.println("Saving file "+i+"/"+informations.keySet().size());
           saveLink(s,"logs/"+fileName+"("+getParameterValueList().replace("\t", ":")+")/"+s+".txt",toPlot);
        }
        saveSystemState("logs/"+fileName+"("+getParameterValueList().replace("\t", ":")+")/"+"SystemState"+".txt");
        
    }
    
    private static void saveSystemState(String filename)
    {
        String out = getParameterValueList()+"\n";
        out+="Changed per state \n";
        out+="\t Mayority \t Confidence \t Weight \t Missfire \n";
        if(systemStateChangeds!=null)
        {
        for(String systemTestType:systemStateChangeds.keySet())
        {
            for(String meth:systemStateChangeds.get(systemTestType).keySet())
            {
                out+=meth+"\t";
            }
            break;
        }
        out+="\n";
        for(String systemTestType:systemStateChangeds.keySet())
        {
            for(String meth:systemStateChangeds.get(systemTestType).keySet())
            {
                out+=systemStateChangeds.get(systemTestType).get(meth)+"\t";
            }
            out+="\n";
        }
        }
        out+="\n";
        out+="\n";
        out+="\t Mayority \t Confidence \t Weight \t Missfire \n";
        if(lastSystemTestPoints!=null)
        for(String s:lastSystemTestPoints.keySet())
        {
        out+="\t"+s+"\t";
        out+=( String.format("%.3f",(lastSystemTestScore.get(s).get("Mayority") / lastSystemTestPoints.get(s)) ));
        out+="\t";
        out+=( String.format("%.3f",(lastSystemTestScore.get(s).get("Confidence") / lastSystemTestPoints.get(s)) ));
        out+="\t";
        out+=( String.format("%.3f",(lastSystemTestScore.get(s).get("Weight") / lastSystemTestPoints.get(s)) ));
        out+="\t";
        out+=(String.format("%.3f",(lastSystemTestScore.get(s).get("Missfire") / lastSystemTestPoints.get(s)) ));
        out+="\n";
        }
    
    File f = new File(filename);
      
       try {
           if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
           
           if (!f.exists())
                f.createNewFile();
           FileWriter w = new FileWriter(f);
           w.write(out);
           w.flush();
           w.close();
       } catch (IOException ex) {
           Logger.getLogger(ScienceTool.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    private static String getParameterLinked(LinkedList<String> toPlot) {
       String out = "";
       for(String s:toPlot)      
       {
           out+=s+"\t";
       }
       out+="\n";
       return out;
    }

    public static void addSystemTestScroe(String state ,HashMap<String, Float> systemTestScores, float systemTestPoints) {
        
        
        if(lastSystemTestPoints == null)
            lastSystemTestPoints = new HashMap<String, Float>();
        
        if(lastSystemTestPoints.containsKey(state))
            lastSystemTestPoints.remove(state);
        
        if(lastSystemTestScore == null)
            lastSystemTestScore = new HashMap<String,HashMap<String, Float>>();
       if(lastSystemTestScore.containsKey(state))
           lastSystemTestScore.remove(state);
        
        lastSystemTestScore.put(state,systemTestScores);
        lastSystemTestPoints.put(state,systemTestPoints);
    }

    public static void addSystemMethodeVariance(HashMap<String, HashMap<String, Integer>> systemStateChanged,float states) {
      state =states;
      systemStateChangeds = systemStateChanged;
      System.out.println("systemStaes "+ systemStateChanged.keySet().size());
    }
    
}
