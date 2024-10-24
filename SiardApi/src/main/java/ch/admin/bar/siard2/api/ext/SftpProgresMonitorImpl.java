package ch.admin.bar.siard2.api.ext;

import com.jcraft.jsch.SftpProgressMonitor;

public class SftpProgresMonitorImpl implements SftpProgressMonitor {

    long count = 0; //The total number of bytes currently received
    long max = 0; //final file size
    long percent = -1; //progress

    /**
     * When each time a data block is transferred, the count method is called, and the parameter of the count method is the data block size of this time.
     */
    @Override
    public boolean count(long count) {
        this.count += count;
        if (percent >= this.count * 100 / max) {
            return true;
        }
        percent = this.count * 100 / max;
        System.out.println("Completed " + this.count + "(" + percent + "%) out of " + max + ".");
        return true;
    }

    /**
     * When the transfer ends, call the end method
     */
    @Override
    public void end() {
        System.out.println("Download file end.");
    }

    /**
     * When the file starts to transfer, call the init method
     */
    @Override
    public void init(int op, String src, String dest, long max) {
        if (op == SftpProgressMonitor.GET) {
            System.out.println("Download file begin.");
        }

        this.max = max;
        this.count = 0;
        this.percent = -1;
    }
}
