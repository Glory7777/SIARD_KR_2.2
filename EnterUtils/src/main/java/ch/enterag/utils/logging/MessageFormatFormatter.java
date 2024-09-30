package ch.enterag.utils.logging;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;























public class MessageFormatFormatter
  extends Formatter
{
  private final String sDEFAULT_FORMAT = "{1,choice,300#D|700#C|800#I|900#W|1000#E} {3,date,yyyy.MM.dd HH:mm:ss}: {4}\n";












  
  public String format(LogRecord record) {
    Object[] arguments = new Object[5];
    arguments[0] = record.getLoggerName();
    arguments[1] = Integer.valueOf(record.getLevel().intValue());
    arguments[2] = Thread.currentThread().getName();
    arguments[3] = new Date(record.getMillis());
    arguments[4] = record.getMessage();
    
    String sFormat = LogManager.getLogManager().getProperty(MessageFormatFormatter.class.getName() + ".pattern");
    
    if (sFormat == null || sFormat.trim().length() == 0)
      sFormat = "{1,choice,300#D|700#C|800#I|900#W|1000#E} {3,date,yyyy.MM.dd HH:mm:ss}: {4}\n"; 
    MessageFormat mf = new MessageFormat(sFormat);
    String s = mf.format(arguments);
    return s;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\logging\MessageFormatFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */