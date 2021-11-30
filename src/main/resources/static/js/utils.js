window.utils = {

    closePopup: function () {
        $(".modal-backdrop").remove();
        $("#myModal").modal("hide");
        $(".divPopup").html('');
    },

    showPopup: function (data) {
        $(".modal-backdrop").remove();
        var html = "";
        html += "<div class=\"modal fade\" id=\"myModal\" tabindex='-1' role=\"dialog\">";
        html += "<div class=\"modal-dialog modal-dialog-centered modal-lg\" role=\"document\">";
        html += "<div class=\"modal-content\">";

        html += data;

        html += "</div>";
        html += " </div>";
        html += "</div>";

        $(".divPopup").html(html);
        $("#myModal").modal("show");
    },

    chart: function (type,data) {
        var chartWrapper = $('.chartjs');
        if (chartWrapper.length) {                                    
            try {
                var data_ = JSON.parse(data);

                if (data_ != null && data_.datasets != null && data_.datasets.length > 0) {
                    // Bổ sung canvas                    
                    var canvas = "";
                    for (var i = 0; i < data_.datasets.length - 1; i++) {
                        canvas += '<canvas class="chartjs mb-2" style="height:400px"></canvas>';
                    }
                    $('.chartjs').after(canvas);

                    // Vẽ biểu đồ
                    for (var i = 0; i < data_.datasets.length; i++) {
                        var obj = {
                            labels: data_.labels,
                            datasets: [
                                data_.datasets[i]
                            ]
                        };
                        utils.chartBase($('.chartjs')[i], type, JSON.stringify(obj));
                    }
                }
            } catch (e) {                
                console.log(e);
            }            
        }        
    },

    chartBase: function (chartWrapper, type, data) {        
        var tooltipShadow = 'rgba(0, 0, 0, 0.25)',
            labelColor = '#6e6b7b',
            grid_line_color = 'rgba(200, 200, 200, 0.2)',
            successColorShade = '#28dac6',
            warningColorShade = '#ffe802',
            warningLightColor = '#FDAC34',
            lineChartDanger = '#ff4961',
            lineChartPrimary = '#666ee8';

        $(chartWrapper).wrap($('<div class="col-md-6"></div>'));

        if (chartWrapper != null) {

            if (type == 'bar') {
                var barChartExample = new Chart(chartWrapper, {
                    type: 'bar',
                    options: {
                        elements: {
                            rectangle: {
                                borderWidth: 2,
                                borderSkipped: 'bottom'
                            }
                        },
                        responsive: true,
                        maintainAspectRatio: false,
                        responsiveAnimationDuration: 500,
                        legend: {
                            display: true,
                            labels: {
                                usePointStyle: true,
                                padding: 25,
                                boxWidth: 9
                            }
                        },
                        tooltips: {
                            // Updated default tooltip UI
                            shadowOffsetX: 1,
                            shadowOffsetY: 1,
                            shadowBlur: 8,
                            shadowColor: tooltipShadow,
                            backgroundColor: window.colors.solid.white,
                            titleFontColor: window.colors.solid.black,
                            bodyFontColor: window.colors.solid.black
                        },
                        scales: {
                            xAxes: [
                                {
                                    barThickness: 15,
                                    display: true,
                                    gridLines: {
                                        display: true,
                                        color: grid_line_color,
                                        zeroLineColor: grid_line_color
                                    },
                                    scaleLabel: {
                                        display: false
                                    },
                                    ticks: {
                                        fontColor: labelColor
                                    }
                                }
                            ],
                            yAxes: [
                                {
                                    display: true,
                                    gridLines: {
                                        color: grid_line_color,
                                        zeroLineColor: grid_line_color
                                    },
                                    ticks: {
                                        //stepSize: 100,
                                        min: 0,
                                        //max: 400,
                                        fontColor: labelColor
                                    }
                                }
                            ]
                        }
                    },
                    data: JSON.parse(data)
                });
            }

            if (type == 'line') {
                var lineExample = new Chart(chartWrapper, {
                    type: 'line',
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        backgroundColor: false,
                        hover: {
                            mode: 'label'
                        },
                        tooltips: {
                            // Updated default tooltip UI
                            shadowOffsetX: 1,
                            shadowOffsetY: 1,
                            shadowBlur: 8,
                            shadowColor: tooltipShadow,
                            backgroundColor: window.colors.solid.white,
                            titleFontColor: window.colors.solid.black,
                            bodyFontColor: window.colors.solid.black
                        },
                        layout: {
                            //padding: {
                            //    top: -15,
                            //    bottom: -25,
                            //    left: -15
                            //}
                        },
                        scales: {
                            xAxes: [
                                {
                                    display: true,
                                    scaleLabel: {
                                        display: true
                                    },
                                    gridLines: {
                                        display: true,
                                        color: grid_line_color,
                                        zeroLineColor: grid_line_color
                                    },
                                    ticks: {
                                        fontColor: labelColor
                                    }
                                }
                            ],
                            yAxes: [
                                {
                                    display: true,
                                    scaleLabel: {
                                        display: true
                                    },
                                    ticks: {
                                        //stepSize: 100,
                                        min: 0,
                                        //max: 400,
                                        fontColor: labelColor
                                    },
                                    gridLines: {
                                        display: true,
                                        color: grid_line_color,
                                        zeroLineColor: grid_line_color
                                    }
                                }
                            ]
                        },
                        legend: {
                            //position: 'top',
                            //align: 'start',
                            labels: {
                                usePointStyle: true,
                                padding: 25,
                                boxWidth: 9
                            }
                        }
                    },
                    data: JSON.parse(data)
                });
            }
        }
    }
}