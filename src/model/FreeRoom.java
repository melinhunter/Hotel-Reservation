package model;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType enumeration){
        super(roomNumber, 0d, enumeration);
    }



    @Override
    public String toString() {
        return "FreeRoom{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", enumeration=" + enumeration +
                '}';
    }
}
