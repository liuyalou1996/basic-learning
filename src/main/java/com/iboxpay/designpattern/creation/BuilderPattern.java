package com.iboxpay.designpattern.creation;

class Computer {

  private String cpu;
  private String mainBoard;
  private String videoCard;

  public Computer(Builder builder) {
    this.cpu = builder.cpu;
    this.mainBoard = builder.mainBoard;
    this.videoCard = builder.videoCard;
  }

  public String getCpu() {
    return cpu;
  }

  public String getMainBoard() {
    return mainBoard;
  }

  public String getVideoCard() {
    return videoCard;
  }

  @Override
  public String toString() {
    return "Computer [cpu=" + cpu + ", mainBoard=" + mainBoard + ", videoCard=" + videoCard + "]";
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    String cpu;
    String mainBoard;
    String videoCard;

    public Builder cpu(String cpu) {
      this.cpu = cpu;
      return this;
    }

    public Builder mainBoard(String mainBoard) {
      this.mainBoard = mainBoard;
      return this;
    }

    public Builder videoCard(String videoCard) {
      this.videoCard = videoCard;
      return this;
    }

    public Computer build() {
      return new Computer(this);
    }
  }

}

public class BuilderPattern {

  public static void main(String[] args) {
    Computer computer = Computer.builder().cpu("酷睿i9").mainBoard("戴尔主板").videoCard("七彩虹显卡").build();
    System.out.println(computer);
  }
}
