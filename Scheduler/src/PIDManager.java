import java.util.BitSet;

public class PIDManager {
    private static final int minPid = 300;
    private static final int maxPid = 5000;
    private BitSet pidBitmap;

    public PIDManager(int minPid, int maxPid) {
        this.minPid = minPid;
        this.maxPid = maxPid;
        this.pidBitmap = new BitSet(this.maxPid - this.minPid + 1);

        this.allocateMap();

    }

    // Creates and initializes a data structure for representing pids
    public int allocateMap() {
        initializePidBitmap();
        return 1;
    }

    // Allocates and returns a pid; returns -1 if unable to allocate a pid (all pids are in use)
    public int allocatePid() {
        int pid = pidBitmap.nextClearBit(0);
        if (pid <= maxPid) {
            pidBitmap.set(pid);
            return pid + minPid;
        } else {
            return -1; // No available PID
        }
    }

    // Releases a pid
    public void releasePid(int pid) {
        if (isValidPid(pid)) {
            pidBitmap.clear(pid - minPid);
        }
    }

    // Initialize the PID bitmap (all PIDs are initially available)
    private void initializePidBitmap() {
        pidBitmap.clear();
    }

    // Checks if a PID is within the valid range
    private boolean isValidPid(int pid) {
        return pid >= minPid && pid <= maxPid;
    }
}