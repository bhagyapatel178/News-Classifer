package uob.oop;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 3.1 - 0.5 marks
     doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 3.2 - 2 marks
    if (_index >=0 && _index < doubElements.length) {
        return doubElements[_index];
    } else {
        return -1 ;} //you need to modify the return value
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 3.3 - 2 marks
    if (_index >=0 && _index < doubElements.length) {
        doubElements[_index] = _value;}
    else if ( _index >= doubElements.length) {
        doubElements[doubElements.length-1] = _value;
    }
    }

    public double[] getAllElements() {
        //TODO Task 3.4 - 0.5 marks

        return doubElements; //you need to modify the return value
    }

    public int getVectorSize() {
        //TODO Task 3.5 - 0.5 marks
        return doubElements.length; //you need to modify the return value
    }

    public Vector reSize(int _size) {
        //TODO Task 3.6 - 6 marks
    if (_size == doubElements.length || _size <= 0) {
        return new Vector(doubElements) ;
    } else {
        double[] reSizedEl = new double [_size];

        for (int index = 0; index< _size; index=index+1) {
            if (index < doubElements.length) {
                reSizedEl[index] = doubElements[index];
            } else { reSizedEl[index]= -1.0;}
        }
        return  new Vector(reSizedEl); } //you need to modify the return value
    }

    public Vector add(Vector _v) {
        //TODO Task 3.7 - 2 marks
    int curLen = doubElements.length;
    int vLen = _v.doubElements.length;

    Vector curVec = new Vector(doubElements);
    Vector vVec = _v;

    if (vLen > curLen) {
        curVec = curVec.reSize(vLen);
    } else {
        vVec = vVec.reSize(curLen);
    }
    int finLength = Math.max(curLen,vLen);
    double [] addedResult = new double [finLength];
    for (int index = 0; index < finLength; index = index +1) {
        addedResult[index] = curVec.doubElements[index] + vVec.doubElements[index];
        }
        return new Vector(addedResult); //you need to modify the return value
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 3.8 - 2 marks
        int curLen = doubElements.length;
        int vLen = _v.doubElements.length;

        Vector curVec = new Vector(doubElements);
        Vector vVec = _v;

        if (vLen > curLen) {
            curVec = curVec.reSize(vLen);
        } else {
            vVec = vVec.reSize(curLen);
        }
        int finLength = Math.max(curLen,vLen);
        double [] subtractedResult = new double [finLength];
        for (int index = 0; index < finLength; index = index +1) {
            subtractedResult [index] = curVec.doubElements[index] - vVec.doubElements[index];
        }
        return new Vector(subtractedResult); //you need to modify the return value
    }

    public double dotProduct(Vector _v) {
        //TODO Task 3.9 - 2 marks
        int curLen = doubElements.length;
        int vLen = _v.doubElements.length;

        Vector curVec = new Vector(doubElements);
        Vector vVec = _v;

        if (vLen > curLen) {
            curVec = curVec.reSize(vLen);
        } else {
            vVec = vVec.reSize(curLen);
        }
        int finLength = Math.max(curLen,vLen);
        double dotResult = 0.0;
        for (int index = 0; index < finLength; index = index +1) {
            dotResult = dotResult + (curVec.doubElements[index] * vVec.doubElements[index]);
        }
        return dotResult; //you need to modify the return value
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 3.10 - 6.5 marks
        int curLen = doubElements.length;
        int vLen = _v.doubElements.length;
        int finLength = Math.max(curLen,vLen);

        Vector curVec = new Vector(doubElements);
        Vector vVec = _v;

        if (vLen > curLen) {
            curVec = curVec.reSize(vLen);
        } else {
            vVec = vVec.reSize(curLen);
        }
        double dotResult = 0.0;
        for (int index = 0; index < finLength; index = index +1) {
            dotResult = dotResult + (curVec.doubElements[index] * vVec.doubElements[index]);
        }
            double magCurVec = 0.0;
            double magVVec = 0.0;
            for (int index = 0; index<finLength;index=index+1){
            magCurVec = magCurVec + (curVec.doubElements[index] * curVec.doubElements[index]);
            magVVec = magVVec + (vVec.doubElements[index] * vVec.doubElements[index] );
            }
        magCurVec = Math.sqrt(magCurVec);
        magVVec = Math.sqrt(magVVec);

        return dotResult/(magCurVec*magVVec); //you need to modify the return value
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;

        if (this.getVectorSize() != v.getVectorSize())
            return false;

        for (int i = 0; i < this.getVectorSize(); i++) {
            if (this.getElementatIndex(i) != v.getElementatIndex(i)) {
                boolEquals = false;
                break;
            }
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
