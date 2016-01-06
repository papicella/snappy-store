///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001, Eric D. Friedman All Rights Reserved.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////
/*
 * Contains changes for GemFireXD distributed data platform.
 *
 * Portions Copyright (c) 2010-2015 Pivotal Software, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.gemstone.gnu.trove;

/**
 * Base class for hashtables that use open addressing to resolve
 * collisions.
 *
 * Created: Wed Nov 28 21:11:16 2001
 *
 * @author Eric D. Friedman
 * @version $Id: THash.java,v 1.7 2003/11/19 15:45:41 ericdf Exp $
 */

abstract public class THash implements Cloneable {
    /** the current number of occupied slots in the hash. */
    protected transient int _size;

    /** the current number of free slots in the hash. */
    protected transient int _free;

    /** the load above which rehashing occurs. */
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;

    /** the default initial capacity for the hash table.  This is one
     * less than a prime value because one is added to it when
     * searching for a prime capacity to account for the free slot
     * required by open addressing. Thus, the real default capacity is
     * 11. */
    public static final int DEFAULT_INITIAL_CAPACITY = 10;

    /** Determines how full the internal table can become before
     * rehashing is required. This must be a value in the range: 0.0 <
     * loadFactor < 1.0.  The default value is 0.5, which is about as
     * large as you can get in open addressing without hurting
     * performance.  Cf. Knuth, Volume 3., Chapter 6.
     */
    protected float _loadFactor;

    /**
     * The maximum number of elements allowed without allocating more
     * space.
     */
    protected int _maxSize;
    
    protected static final int CONDITIONAL_COMPACT_FACTOR = 2;
    
    /**
     * Creates a new <code>THash</code> instance with the default
     * capacity and load factor.
     */
    public THash() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates a new <code>THash</code> instance with a prime capacity
     * at or near the specified capacity and with the default load
     * factor.
     *
     * @param initialCapacity an <code>int</code> value
     */
    public THash(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates a new <code>THash</code> instance with a prime capacity
     * at or near the minimum needed to hold <tt>initialCapacity</tt>
     * elements with load factor <tt>loadFactor</tt> without triggering
     * a rehash.
     *
     * @param initialCapacity an <code>int</code> value
     * @param loadFactor a <code>float</code> value
     */
    public THash(int initialCapacity, float loadFactor) {
        super();
        _loadFactor = loadFactor;
        setUp((int)Math.ceil(initialCapacity / loadFactor));
    }

    @Override // GemStoneAddition
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnse) {
            return null; // it's supported
        }
    }
    
    /**
     * Tells whether this set is currently holding any elements.
     *
     * @return a <code>boolean</code> value
     */
    public boolean isEmpty() {
        return 0 == _size;
    }

    /**
     * Returns the number of distinct elements in this collection.
     *
     * @return an <code>int</code> value
     */
    public int size() {
        return _size;
    }

    /**
     * @return the current physical capacity of the hash table.
     */
    abstract protected int capacity();
    
    /**
     * Ensure that this hashtable has sufficient capacity to hold
     * <tt>desiredCapacity<tt> <b>additional</b> elements without
     * requiring a rehash.  This is a tuning method you can call
     * before doing a large insert.
     *
     * @param desiredCapacity an <code>int</code> value
     */
    public void ensureCapacity(int desiredCapacity) {
        if (desiredCapacity > (_maxSize - size())) {
            rehash(PrimeFinder.nextPrime((int)Math.ceil(desiredCapacity + size() /
                                                        _loadFactor) + 1));
            computeMaxSize(capacity());
        }
    }
    
    /**
     * Compresses the hashtable to the minimum prime size (as defined
     * by PrimeFinder) that will hold all of the elements currently in
     * the table.  If you have done a lot of <tt>remove</tt>
     * operations and plan to do a lot of queries or insertions or
     * iteration, it is a good idea to invoke this method.  Doing so
     * will accomplish two things:
     *
     * <ol>
     * <li> You'll free memory allocated to the table but no
     * longer needed because of the remove()s.</li>
     *
     * <li> You'll get better query/insert/iterator performance
     * because there won't be any <tt>REMOVED</tt> slots to skip
     * over when probing for indices in the table.</li>
     * </ol>
     */
    public void compact() {
        // need at least one free spot for open addressing
        rehash(PrimeFinder.nextPrime((int)Math.ceil(size()/_loadFactor) + 1));
        computeMaxSize(capacity());
    }
    
    // GemStoneAddition
    /**
     * Calls compact by taking next set expansion into account.
     * The set is expanded based on the capacity and load factor (default .5)
     * this method calls the compact if the size is well below next expansion.
     */
    public void conditionalCompact(){
      if (_size < (capacity() * (_loadFactor/CONDITIONAL_COMPACT_FACTOR)))
      {
        compact();
      }
    }

    /**
     * This simply calls {@link #compact compact}.  It is included for 
     * symmetry with other collection classes.  Note that the name of this
     * method is somewhat misleading (which is why we prefer
     * <tt>compact</tt>) as the load factor may require capacity above
     * and beyond the size of this collection.
     *
     * @see #compact
     */
    public final void trimToSize() {
        compact();
    }

    /**
     * Delete the record at <tt>index</tt>.  Reduces the size of the
     * collection by one.
     *
     * @param index an <code>int</code> value
     */
    protected void removeAt(int index) {
        _size--;
    }

    /**
     * Empties the collection.
     */
    public void clear() {
        _size = 0;
        _free = capacity();
    }
    
    /**
     * initializes the hashtable to a prime capacity which is at least
     * <tt>initialCapacity + 1</tt>.  
     *
     * @param initialCapacity an <code>int</code> value
     * @return the actual capacity chosen
     */
    protected int setUp(int initialCapacity) {
        int capacity;

        capacity = PrimeFinder.nextPrime(initialCapacity);
        computeMaxSize(capacity);
        return capacity;
    }

    /**
     * Rehashes the set.
     *
     * @param newCapacity an <code>int</code> value
     */
    protected abstract void rehash(int newCapacity);

    /**
     * Computes the values of maxSize. There will always be at least
     * one free slot required.
     *
     * @param capacity an <code>int</code> value
     */
    private final void computeMaxSize(int capacity) {
        // need at least one free slot for open addressing
        _maxSize = Math.min(capacity - 1,
                            (int)Math.floor(capacity * _loadFactor));
        _free = capacity - _size; // reset the free element count
    }

    /**
     * After an insert, this hook is called to adjust the size/free
     * values of the set and to perform rehashing if necessary.
     */
    protected final void postInsertHook(boolean usedFreeSlot) {
        if (usedFreeSlot) {
            _free--;
        }
        
        // rehash whenever we exhaust the available space in the table
        if (++_size > _maxSize || _free == 0) {
            // choose a new capacity suited to the new state of the table
            // if we've grown beyond our maximum size, double capacity;
            // if we've exhausted the free spots, rehash to the same capacity,
            // which will free up any stale removed slots for reuse.
            int newCapacity = _size > _maxSize ? PrimeFinder.nextPrime(capacity() << 1) : capacity();
            rehash(newCapacity);
            computeMaxSize(capacity());
        }
    }
}// THash