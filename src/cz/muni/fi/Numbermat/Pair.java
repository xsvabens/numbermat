/*
    This file is part of Numbermat: Math Problem Generator.
    Copyright © 2014 Valdemar Svabensky

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package cz.muni.fi.Numbermat;

import java.util.Objects;

/**
 * Generic pair class.
 * 
 * @author Valdemar Svabensky <395868(at)mail(dot)muni(dot)cz>
 */
public final class Pair<F extends Comparable<F>, S extends Comparable<S>> 
    implements Comparable<Pair<F,S>> {
    
    private F first;
    private S second;
    
    public Pair() {
    }
    
    public Pair(final F first) {
        this.first = first;
    }

    public Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) (obj);
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final int hash1 = this.first.hashCode();
        final int hash2 = this.second.hashCode();
        return hash1 * hash2;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("(").append(first).append(", ")
                .append(second).append(")").toString();
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
    
    public void setFirst(final F first) {
        this.first = first;
    }

    public void setSecond(final S second) {
        this.second = second;
    }
    
    public boolean isEmpty() {
        return this.equals(new Pair<F, S>());
    }

    @Override
    public int compareTo(final Pair<F, S> that) {
        int cmp = this.getFirst().compareTo(that.getFirst());
        if (cmp == 0)
            cmp = this.getSecond().compareTo(that.getSecond());
        return cmp;
    }
}
