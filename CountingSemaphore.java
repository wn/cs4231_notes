/* 
 * Implementation of a counting semaphore using two mutexes.
 */
class CountingSemaphore {
    int count;
    Mutex semaphoreCount; // Allow only P() or V() to run, ensure that count is "atomic".
    Mutex semaphoreBlocker; // Used to actually block threads if count is out of bound.
    int n; 

    CountingSemaphore(int n) {
        self.n = n;
        count = n;
        semaphoreCount = Mutex(1);
        semaphoreBlocker = Mutex(1);
    }

    // Decrease counter
    void P() {
        semaphoreCount.P();
        if (count == 0) {
            semaphoreBlocker.P();
        } else if (count == n) {
            semaphoreBlocker.V();
        }
        count--;
        semaphoreCount.V();
    }
    
    // Increase counter
    void V() {
        semaphoreCount.P();
        if (count == 0) {
            semaphoreBlocker.V();
        } else if (count == n) {
            semaphoreBlocker.P();
        }
        count++;
        semaphoreCount.V();
    }
}