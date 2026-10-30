package com.airportcli.domain;

public class City {
    private Long id;
    private String name;
    private String province;
    private int population;

    public City() {}

    public City(Long id, String name, String province, int population) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.population = population;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
