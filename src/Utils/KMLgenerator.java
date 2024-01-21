//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Utils;

import GNSS.Sat;
import Geometry.Line3D;
import Geometry.Point3D;
import dataStructres.SirfPeriodicMeasurement;
import dataStructres.SirfSVMeasurement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class KMLgenerator {
    public KMLgenerator() {
    }

    public static void generateSatLinesFromSat(Sat sat, Point3D receiverKnownPos, String filePath) throws IOException {
        new String("");
        int i = false;
        FileWriter fstream = new FileWriter(filePath);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write("<Document>\n");
        Point3D ReciverPosInLatLong = GeoUtils.convertUTMtoLATLON(receiverKnownPos, 36);
        double lat1 = ReciverPosInLatLong.getX();
        double lon1 = ReciverPosInLatLong.getY();
        out.write("\n\n<Style id=\"green\">\n");
        out.write("<LineStyle>\n");
        out.write("<color>ff0000ff</color>\n<scale>0.5</scale>\n</LineStyle>\n");
        out.write("</Style>\n");
        double az = sat.getAzimuth();
        double el = sat.getElevetion();
        out.write("\n\n<Placemark>\n");
        out.write(" <styleUrl>#green</styleUrl>\n");
        out.write("<LineString>\n");
        out.write("<tessellate>1</tessellate>\n");
        out.write("<altitudeMode>relativeToGround</altitudeMode>\n");
        out.write("<coordinates>\n");
        Line3D ray = new Line3D(receiverKnownPos, az, el, 300);
        Point3D rayPosUTM = ray.getP2();
        Point3D rayPosLatlong = GeoUtils.convertUTMtoLATLON(rayPosUTM, 36);
        String line = lon1 + "," + lat1 + "," + Double.toString(receiverKnownPos.getZ()) + "  " + Double.toString(rayPosLatlong.getY()) + "," + Double.toString(rayPosLatlong.getX()) + "," + Double.toString(rayPosLatlong.getZ());
        out.write(line);
        out.write("\n</coordinates>\n");
        out.write("</LineString>\n");
        out.write("</Placemark>\n");
        out.write("\n</Document>");
        out.close();
    }

    public static void generateSatLinesFromSirfSvMesserment(List<SirfPeriodicMeasurement> sirfMeas, Point3D receiverKnownPos, String filePath) {
        new String("");
        int i = false;

        try {
            FileWriter fstream = new FileWriter(filePath);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("<Document>\n");
            Point3D ReciverPosInLatLong = GeoUtils.convertUTMtoLATLON(receiverKnownPos, 36);
            double lat1 = ReciverPosInLatLong.getX();
            double lon1 = ReciverPosInLatLong.getY();
            out.write("\n\n<Style id=\"green\">\n");
            out.write("<LineStyle>\n");
            out.write("<color>ff0000ff</color>\n<scale>0.5</scale>\n</LineStyle>\n");
            out.write("</Style>\n");
            SirfPeriodicMeasurement sirfMeasure = (SirfPeriodicMeasurement)sirfMeas.get(sirfMeas.size() / 2);
            Iterator var18 = sirfMeasure.getSatellites().keySet().iterator();

            while(var18.hasNext()) {
                Integer PRN = (Integer)var18.next();
                SirfSVMeasurement satMeas = (SirfSVMeasurement)sirfMeasure.getSatellites().get(PRN);
                if (PRN == 1) {
                    double az = satMeas.getAzimuth();
                    double el = 88.0;
                    if (receiverKnownPos == null) {
                        receiverKnownPos = sirfMeasure.GetPosInUTM();
                    }

                    out.write("\n\n<Placemark>\n");
                    out.write(" <styleUrl>#green</styleUrl>\n");
                    out.write("<LineString>\n");
                    out.write("<tessellate>1</tessellate>\n");
                    out.write("<BallonStyle><text>" + PRN + " " + az + " " + el + "</text></BallonStyle>\n");
                    out.write("<altitudeMode>relativeToGround</altitudeMode>\n");
                    out.write("<coordinates>\n");
                    Line3D ray = new Line3D(receiverKnownPos, az, el, 1500);
                    Point3D rayPosUTM = ray.getP2();
                    Point3D rayPosLatlong = GeoUtils.convertUTMtoLATLON(rayPosUTM, 36);
                    String line = lon1 + "," + lat1 + "," + Double.toString(receiverKnownPos.getZ()) + "  " + Double.toString(rayPosLatlong.getY()) + "," + Double.toString(rayPosLatlong.getX()) + "," + Double.toString(rayPosLatlong.getZ());
                    out.write(line);
                    out.write("\n</coordinates>\n");
                    out.write("</LineString>\n");
                    out.write("</Placemark>\n");
                }
            }

            out.write("\n</Document>");
            out.close();
        } catch (IOException var28) {
            var28.printStackTrace();
        }

    }

    public static void generateTimeStampSignleSatLinesFromSirfSvMesserment(List<SirfPeriodicMeasurement> sirfMeas, int PRN, Point3D receiverKnownPos, String filePath) {
        new String("");
        int i = false;

        try {
            FileWriter fstream = new FileWriter(filePath);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("<Document>\n");
            Point3D ReciverPosInLatLong = GeoUtils.convertUTMtoLATLON(receiverKnownPos, 36);
            double lat1 = ReciverPosInLatLong.getX();
            double lon1 = ReciverPosInLatLong.getY();
            out.write("\n\n<Style id=\"green\">\n");
            out.write("<LineStyle>\n");
            out.write("<color>ff0000ff</color>\n<scale>0.5</scale>\n</LineStyle>\n");
            out.write("</Style>\n");

            for(int j = 0; j < sirfMeas.size(); ++j) {
                SirfSVMeasurement satMeas = (SirfSVMeasurement)((SirfPeriodicMeasurement)sirfMeas.get(j)).getSatellites().get(PRN);
                if (satMeas != null) {
                    double az = satMeas.getAzimuth();
                    double el = satMeas.getElevation();
                    if (receiverKnownPos == null) {
                        receiverKnownPos = ((SirfPeriodicMeasurement)sirfMeas.get(j)).GetPosInUTM();
                    }

                    out.write("\n\n<Placemark>\n");
                    out.write(" <styleUrl>#green</styleUrl>\n");
                    out.write("<LineString>\n");
                    out.write("<TimeStamp>\n");
                    out.write("<when>" + j + "</when>\n");
                    out.write("</TimeStamp>\n");
                    out.write("<tessellate>1</tessellate>\n");
                    out.write("<altitudeMode>relativeToGround</altitudeMode>\n");
                    out.write("<coordinates>\n");
                    Line3D ray = new Line3D(receiverKnownPos, az, el, 1500);
                    Point3D rayPosUTM = ray.getP2();
                    Point3D rayPosLatlong = GeoUtils.convertUTMtoLATLON(rayPosUTM, 36);
                    String line = lon1 + "," + lat1 + ",1.5   " + Double.toString(rayPosLatlong.getY()) + "," + Double.toString(rayPosLatlong.getX()) + "," + Double.toString(rayPosLatlong.getZ());
                    out.write(line);
                    out.write("\n</coordinates>\n");
                    out.write("</LineString>\n");
                    out.write("</Placemark>\n");
                }
            }

            out.write("\n</Document>");
            out.close();
        } catch (IOException var27) {
            var27.printStackTrace();
        }

    }

    public static void GenerateTimeStampKMLfromList(List<Point3D> PointList, String FilePath) {
        new String("");
        int i = false;

        try {
            FileWriter fstream = new FileWriter(FilePath);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("<Document>\n");
            out.write("<Style id=\"red\">\n");
            out.write("<IconStyle>\n");
            out.write("<color>00ff00ff</color>\n<scale>0.5</scale>\n</IconStyle>\n");
            out.write("</Style>\n");
            out.write("\n\n<Style id=\"green\">\n");
            out.write("<IconStyle>\n");
            out.write("<color>ff0000ff</color>\n<scale>0.5</scale>\n</IconStyle>\n");
            out.write("</Style>\n");
            System.out.println("");

            for(int i = 0; i < PointList.size(); ++i) {
                out.write("\n\n<Placemark>\n");
                out.write(" <styleUrl>#green</styleUrl>\n");
                out.write("<Style>\n<BalloonStyle>\n<text>This point was taken at time " + i + "</text>\n</BalloonStyle>\n</Style>\n ");
                out.write("<TimeStamp>\n");
                out.write("<when>" + i + "</when>\n");
                out.write(" </TimeStamp>\n");
                out.write("<Point>\n<altitudeMode>relativeToGround</altitudeMode>\n<coordinates>");
                Point3D tmp = (Point3D)PointList.get(i);
                tmp = GeoUtils.convertUTMtoLATLON((Point3D)PointList.get(i), 36);
                double lat = tmp.getX();
                double lon = tmp.getY();
                double alt = tmp.getZ();
                String line = Double.toString(lon) + "," + Double.toString(lat) + "," + Double.toString(alt);
                out.write(line);
                out.write("</coordinates>\n");
                out.write("</Point>\n</Placemark>");
            }

            out.write("</Document>");
            out.close();
        } catch (IOException var13) {
            var13.printStackTrace();
        }

    }
}
