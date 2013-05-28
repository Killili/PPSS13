package wsn.pp.messages;

/**
 * This class is automatically generated by mig. DO NOT EDIT THIS FILE.
 * This class implements a Java interface to the 'ConfigMsg'
 * message type.
 */

public class ConfigMsg extends net.tinyos.message.Message {

    /** The default size of this message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 3;

    /** The Active Message type associated with this message. */
    public static final int AM_TYPE = 6;

    /** Create a new ConfigMsg of size 3. */
    public ConfigMsg() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new ConfigMsg of the given data_length. */
    public ConfigMsg(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new ConfigMsg with the given data_length
     * and base offset.
     */
    public ConfigMsg(int data_length, int base_offset) {
        super(data_length, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new ConfigMsg using the given byte array
     * as backing store.
     */
    public ConfigMsg(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new ConfigMsg using the given byte array
     * as backing store, with the given base offset.
     */
    public ConfigMsg(byte[] data, int base_offset) {
        super(data, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new ConfigMsg using the given byte array
     * as backing store, with the given base offset and data length.
     */
    public ConfigMsg(byte[] data, int base_offset, int data_length) {
        super(data, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new ConfigMsg embedded in the given message
     * at the given base offset.
     */
    public ConfigMsg(net.tinyos.message.Message msg, int base_offset) {
        super(msg, base_offset, DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new ConfigMsg embedded in the given message
     * at the given base offset and length.
     */
    public ConfigMsg(net.tinyos.message.Message msg, int base_offset, int data_length) {
        super(msg, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this message. Includes the
     * message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <ConfigMsg> \n";
      try {
        s += "  [signalStrength=0x"+Long.toHexString(get_signalStrength())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [interval=0x"+Long.toHexString(get_interval())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      return s;
    }

    // Message-type-specific access methods appear below.

    /////////////////////////////////////////////////////////
    // Accessor methods for field: signalStrength
    //   Field type: short, unsigned
    //   Offset (bits): 0
    //   Size (bits): 8
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'signalStrength' is signed (false).
     */
    public static boolean isSigned_signalStrength() {
        return false;
    }

    /**
     * Return whether the field 'signalStrength' is an array (false).
     */
    public static boolean isArray_signalStrength() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'signalStrength'
     */
    public static int offset_signalStrength() {
        return (0 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'signalStrength'
     */
    public static int offsetBits_signalStrength() {
        return 0;
    }

    /**
     * Return the value (as a short) of the field 'signalStrength'
     */
    public short get_signalStrength() {
        return (short)getUIntBEElement(offsetBits_signalStrength(), 8);
    }

    /**
     * Set the value of the field 'signalStrength'
     */
    public void set_signalStrength(short value) {
        setUIntBEElement(offsetBits_signalStrength(), 8, value);
    }

    /**
     * Return the size, in bytes, of the field 'signalStrength'
     */
    public static int size_signalStrength() {
        return (8 / 8);
    }

    /**
     * Return the size, in bits, of the field 'signalStrength'
     */
    public static int sizeBits_signalStrength() {
        return 8;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: interval
    //   Field type: int, unsigned
    //   Offset (bits): 8
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'interval' is signed (false).
     */
    public static boolean isSigned_interval() {
        return false;
    }

    /**
     * Return whether the field 'interval' is an array (false).
     */
    public static boolean isArray_interval() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'interval'
     */
    public static int offset_interval() {
        return (8 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'interval'
     */
    public static int offsetBits_interval() {
        return 8;
    }

    /**
     * Return the value (as a int) of the field 'interval'
     */
    public int get_interval() {
        return (int)getUIntBEElement(offsetBits_interval(), 16);
    }

    /**
     * Set the value of the field 'interval'
     */
    public void set_interval(int value) {
        setUIntBEElement(offsetBits_interval(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'interval'
     */
    public static int size_interval() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'interval'
     */
    public static int sizeBits_interval() {
        return 16;
    }

}
