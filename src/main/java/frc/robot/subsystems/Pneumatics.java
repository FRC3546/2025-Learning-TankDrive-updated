package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
    
    private final Compressor m_compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    private final DoubleSolenoid m_solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5);
    
    public enum pneumaticStates{
        open,
        close,
        toggle
    }

    public Pneumatics(){
        m_compressor.enableDigital();
    }

    public void setPneumaticState(pneumaticStates setState){
        
        switch (setState){
            case open:
            m_solenoid.set(DoubleSolenoid.Value.kForward);
            break;
            case close:
            m_solenoid.set(DoubleSolenoid.Value.kReverse);
            break;
            case toggle:
            m_solenoid.toggle();
            break;
        }
    }

    
}
