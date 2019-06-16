import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import Moment from 'react-moment';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import CircularProgress from 'material-ui/CircularProgress';

const WebService =({dateTime})=>{
            return (
                <div>
                    <Moment format="YYYY-MM-DD HH:mm">
                    {dateTime}
                </Moment>
                </div>
            )
}

class TimeComponent extends Component{
    constructor(props) {
        super(props);
        this.state={
            time: "",
            loading: true
        }
    }

    componentDidMount(): void {
        const timeWebServiceUrl = "http://worldclockapi.com/api/json/cet/now"
        fetch(timeWebServiceUrl).then(res => res.json())
            .then(data => {
                this.setState({ time: data.currentDateTime })
                this.setState({loading: false})
            });
    }

    render(){
        return(
            <div>
                {this.props.t('webservice', { framework: "react-i18next" })}
                {this.state.loading
                    ?<MuiThemeProvider>
                        <div style={{display: 'block', textAlign: 'center'}}>
                            <CircularProgress />
                            </div>
                    </MuiThemeProvider>
                    :<WebService dateTime={this.state.time}/>}
            </div>
        )
    }
}

export default translate('common')(TimeComponent);