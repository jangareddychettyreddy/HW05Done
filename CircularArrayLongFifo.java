package com.abc.sync;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link LongFifo} which uses a circular array internally.
 * <p>
 * Look at the documentation in LongFifo to see how the methods are supposed to
 * work.
 * <p>
 * The data is stored in the slots array. count is the number of items currently
 * in the FIFO. When the FIFO is not empty, head is the index of the next item
 * to remove. When the FIFO is not full, tail is the index of the next available
 * slot to use for an added item. Both head and tail need to jump to index 0
 * when they "increment" past the last valid index of slots (this is what makes
 * it circular).
 * <p>
 * See <a href="https://en.wikipedia.org/wiki/Circular_buffer">Circular Buffer
 * on Wikipedia</a> for more information.
 */
public class CircularArrayLongFifo implements LongFifo {
    // do not change any of these fields:
    private final long[] slots;
    private int head;
    private int tail;
    private int count;
    private final Object lockObject;
    private boolean flag3 = false;
    private int z;
	private long counter;
	private boolean flag4 = false;
    // this constructor is correct as written - do not change
    public CircularArrayLongFifo(int fixedCapacity,
                                 Object proposedLockObject) {

        lockObject =
            proposedLockObject != null ? proposedLockObject : new Object();

        slots = new long[fixedCapacity];
        head = 0;
        tail = 0;
        count = 0;
    }

    // this constructor is correct as written - do not change
    public CircularArrayLongFifo(int fixedCapacity) {
        this(fixedCapacity, null);
    }

    // this method is correct as written - do not change
    @Override
    public int getCount() {
        synchronized ( lockObject ) {
            return count;
        }
    }

    // this method is correct as written - do not change
    @Override
    public boolean isEmpty() {
        synchronized ( lockObject ) {
            return count == 0;
        }
    }

    // this method is correct as written - do not change
    @Override
    public boolean isFull() {
        synchronized ( lockObject ) {
            return count == slots.length;
        }
    }

    @Override
    public void clear() {
        // FIXME
    	for (int z = tail; z < head; z++) {
			try {
				remove();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

    }

    // this method is correct as written - do not change
    @Override
    public int getCapacity() {
        return slots.length;
    }

    @Override
    public boolean add(long value, long msTimeout) throws InterruptedException {
        // FIXME
    	TimeUnit.SECONDS.sleep(msTimeout);
    	System.out.println("\n Waiting TStimeout"+msTimeout+"seconds \n");
    	if (isFull() == false) {
			slots[head] = value;
			count++;
			head++;
			for (z = 0; z < count; z++) {
				System.out.println("\n"+slots[z] + " number is at position " + z);
			}
			System.out.println("Head position is at " + head + " and count is: " + count + "\n");
			flag3 = true;
		} else
			flag3 = false;
		return flag3;
        
    }

    @Override
    public void add(long value) throws InterruptedException {
        // FIXME
    	if (isFull() == true) {
    		System.out.println("\n Array is full and it will wait \n");
    		add(value, 2);
    	}
    	else
    	{
    		System.out.println("\n Array is not full \n");
    		add(value,0);
    	}
    	

    }

    @Override
    public long remove() throws InterruptedException {
        // FIXME
    	waitUntilEmpty();
        return 0;
    }

    @Override
    public boolean waitUntilEmpty(long msTimeout) throws InterruptedException {
        // FIXME
    	TimeUnit.SECONDS.sleep(msTimeout);
    	System.out.println("\n Waiting TStimeout"+msTimeout+"seconds \n");
    	if (isEmpty() == false && count == 0) {
			counter = slots[0];
		} else {
			counter = slots[tail];

		}
		System.out.println("\n"+"Removed number is: " + counter +" and tail position is at "+tail);
		tail++;
		count--;
		//flag4 = LongFifo.RemoveResult.createValid(counter);
		return flag4;

        //return false;
    }

    @Override
    public void waitUntilEmpty() throws InterruptedException {
        // FIXME
    	if(isEmpty()==false){
    		System.out.println("\n Array is not empty and it will wait \n");
    		waitUntilEmpty(2);
    	}
    	else{
    		System.out.println("\n Array is empty \n");
    		waitUntilEmpty(0);	
    	}
    		
    }

    // this method is correct as written - do not change
    @Override
    public Object getLockObject() {
        return lockObject;
    }
}

