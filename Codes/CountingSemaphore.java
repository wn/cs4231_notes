/* 
 * Implementation of a counting semaphore using two mutexes.
 * 
 * Note: count is assumed to never go over n.
 */
class CountingSemaphore {
    private int count;
    private Mutex semaphoreCount; // Allow only P() or V() to run, ensure that count is "atomic".
    private Mutex semaphoreBlocker; // Used to actually block threads if count is out of bound.
    private final int n; 

    public CountingSemaphore(int n) {
        self.n = n;
        count = n;
        semaphoreCount = Mutex(1);
        semaphoreBlocker = Mutex(0); // Should be initialised to 0.
    }

    // Decrease counter
    public void P() {
        boolean cannotEnter = false;
        semaphoreCount.P();
        if (count <= 0) {
            cannotEnter = true;
        }
        count--;
        semaphoreCount.V();
        if (cannotEnter) {
            semaphoreBlocker.P();
        }
    }
    
    // Increase counter
    public void V() {
        boolean toRelease = false;
        semaphoreCount.P();
        if (count < 0) {
            toRelease = true;
        }
        count++;
        if (toRelease) {
            semaphoreBlocker.V();
        }
        semaphoreCount.V();
    }
}
