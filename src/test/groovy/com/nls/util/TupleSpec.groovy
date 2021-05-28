package com.nls.util

import spock.lang.Specification

class TupleSpec extends Specification {
    def "I can create a tuple"() {
        when:
        Tuple<String, Integer> tuple = new Tuple<>('String', 10)

        then:
        tuple.key == 'String'
        tuple.value == 10
    }

    def "Equals and hashcode works"() {
        when:
        Tuple<String, Integer> t1 = new Tuple<>('String', 10)
        Tuple<String, Integer> t2 = new Tuple<>('String', 10)
        Tuple<String, Integer> t3 = new Tuple<>('String', 11)
        Tuple<String, Integer> t4 = new Tuple<>('Rope', 10)

        then:
        t1.equals(t2)
        t2.equals(t1)
        !t1.equals(t3)
        !t1.equals(t4)

        and:
        t1.hashCode() == t2.hashCode()
        t1.hashCode() != t3.hashCode()
        t1.hashCode() != t4.hashCode()
    }

    def "I can convert a map to a list of tuples"() {
        when:
        Map<String, Integer> map = [String: 10, Rope: 11]
        List<Tuple<String, Integer>> tuples = Tuple.fromMap(map)
        tuples.sort {it.key }

        then:
        tuples.size() == 2
        tuples[0].key == 'Rope'
        tuples[0].value == 11
        tuples[1].key == 'String'
        tuples[1].value == 10
    }

    def "I can convert a list of tuples to a map"() {
        when:
        List<Tuple<String, Integer>> tuples = [new Tuple<>('String', 10), new Tuple<>('Rope', 11)]
        Map<String, Integer> map = Tuple.toMap(tuples)

        then:
        map.size() == 2
        map['String'] == 10
        map['Rope'] == 11
    }

    def "I can convert a list of tuples to a grouped map"() {
        when:
        List<Tuple<String, Integer>> tuples = [new Tuple<>('String', 10), new Tuple<>('Rope', 11), new Tuple<>('Rope', 13)]
        Map<String, List<Integer>> map = Tuple.toGroupedMap(tuples)

        then:
        map.size() == 2
        map['String'] as Set == [10] as Set
        map['Rope'] as Set == [11, 13] as Set
    }
}
